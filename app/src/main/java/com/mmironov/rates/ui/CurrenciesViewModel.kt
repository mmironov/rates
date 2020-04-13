package com.mmironov.rates.ui

import androidx.lifecycle.ViewModel
import com.mmironov.rates.data.repository.CurrencyRepository
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class CurrenciesViewModel(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {
    val currencyRates by lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            currencyRepository.getRates()
        }
    }
}