package com.mmironov.rates.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmironov.rates.R
import com.mmironov.rates.dagger.DaggerCurrencyComponent
import com.mmironov.rates.ui.list.CurrencyAdapter
import kotlinx.android.synthetic.main.fragment_currencies.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrenciesFragment : ScopedFragment() {

    @Inject lateinit var viewModelFactory: CurrenciesViewModelFactory
    private val viewModel: CurrenciesViewModel by viewModels(factoryProducer = {viewModelFactory})
    private lateinit var adapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerCurrencyComponent.create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_currencies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupAdapter()
        bindUI()
    }

    private fun setupAdapter() {
        adapter = CurrencyAdapter()
        adapter.onBaseCurrencyChangedListener = {
            launch(Dispatchers.IO) {
                viewModel.stopFetchingRates()
                viewModel.saveRates(it)
                viewModel.startFetchingRates()
            }
        }
        currencies_list.adapter = adapter
        currencies_list.layoutManager = LinearLayoutManager(context)
    }

    private fun bindUI() = launch {
        val rates = viewModel.currencyRates.await()
        rates.observe(viewLifecycleOwner, Observer {
            if (it != null && it.isNotEmpty()) {
                adapter.updateCurrencies(it)
            }
        })
    }
}