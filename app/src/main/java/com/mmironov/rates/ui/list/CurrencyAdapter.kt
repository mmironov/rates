package com.mmironov.rates.ui.list

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.Observable
import androidx.recyclerview.widget.RecyclerView
import com.mmironov.rates.RatesApp
import com.mmironov.rates.data.db.Currency
import com.mmironov.rates.databinding.CurrencyRowBinding

class CurrencyAdapter(
    private val currencies: MutableList<CurrencyRow> = mutableListOf()
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    private lateinit var recyclerView: RecyclerView
    private var rates: MutableList<Currency> = mutableListOf()

    var onBaseCurrencyChangedListener: ((List<Currency>) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CurrencyRowBinding.inflate(inflater, parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun getItemCount() = currencies.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(currencies[position])
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    fun updateCurrencies(newCurrencies: List<Currency>) {
        rates.clear()
        rates.addAll(newCurrencies)

        if (currencies.size == 0) {
            initAmounts(newCurrencies)
        } else {
            recalculateAmounts()
        }

        currencies[0].addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                recalculateAmounts()
            }
        })
        notifyDataSetChanged()
    }

    private fun initAmounts(newCurrencies: List<Currency>) {
        val rows = newCurrencies.map {
            val row = CurrencyRow(
                icon = "ic_${it.code}".toDrawable(),
                code = it.code,
                description = it.code.toCurrencyName(),
                index = it.index
            )
            row.amount = amountFromat.format(it.rate)
            row
        }

        currencies.clear()
        currencies.addAll(rows)
    }

    private fun recalculateAmounts() {
        val toExchange = currencies[0].amount.toDoubleOrNull() ?: 0.0
        currencies.drop(1).forEachIndexed { index, row ->
            val amount = rates[index+1].rate * toExchange
            row.amount = amountFromat.format(amount)
        }
    }

    inner class CurrencyViewHolder(private val binding: CurrencyRowBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(currencyRow: CurrencyRow) {
            binding.currency = currencyRow
            if (layoutPosition == 0) {
                Log.d("FirstRow", "${currencyRow.amount} : ${currencyRow.hashCode()}");
            }
            binding.row.setOnClickListener(moveFirst())
            binding.executePendingBindings()
        }

        private fun moveFirst(): (View) -> Unit = {
            layoutPosition.takeIf { it > 0 }?.also {currentPosition ->
                currencies.removeAt(currentPosition).also {
                    currencies.add(0, it.copy())
                }
                rates.removeAt(currentPosition).also {
                    rates.add(0, it.copy())
                }

                recyclerView.smoothScrollToPosition(0)

                val updatedRates = rates.mapIndexed { index, currency ->
                    currency.copy(index = index)
                }
                onBaseCurrencyChangedListener?.let { it(updatedRates) }
            }
        }
    }
}

fun String.toDrawable(): Drawable {
    val resId = RatesApp.context.resources
        .getIdentifier(this.toLowerCase(), "drawable", RatesApp.context.packageName)
    return if (resId != 0)
        ContextCompat.getDrawable(RatesApp.context, resId)!!
    else "ic_default".toDrawable()
}

private fun String.toCurrencyName(): String {
    val resId = RatesApp.context.resources
        .getIdentifier(this.toLowerCase(), "string", RatesApp.context.packageName)
    return if (resId != 0)
        RatesApp.context.resources.getString(resId)
    else "default_currency".toCurrencyName()
}

private const val amountFromat = "%10.2f"