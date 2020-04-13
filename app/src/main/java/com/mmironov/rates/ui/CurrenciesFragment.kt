package com.mmironov.rates.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mmironov.rates.R
import com.mmironov.rates.dagger.DaggerCurrencyComponent
import kotlinx.android.synthetic.main.fragment_currencies.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrenciesFragment : ScopedFragment() {

    @Inject lateinit var viewModelFactory: CurrenciesViewModelFactory
    private lateinit var viewModel: CurrenciesViewModel

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

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CurrenciesViewModel::class.java)

        bind()
    }

    private fun bind() = launch {
        val rates = viewModel.currencyRates.await()
        rates.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                text.text = it.toString()
            }
        })
    }
}