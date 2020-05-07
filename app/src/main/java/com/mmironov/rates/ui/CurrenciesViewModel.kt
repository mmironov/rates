package com.mmironov.rates.ui

import androidx.lifecycle.ViewModel
import com.mmironov.rates.data.db.Currency
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

    suspend fun restartFetchingRates() {
        if (currencyRepository.isFetching) {
            stopFetchingRates()
        }
        currencyRepository.getRates()
    }

    fun stopFetchingRates()  {
        currencyRepository.cancelFetching()
    }

    fun saveRates(currencies: List<Currency>) {
        currencyRepository.saveCurrencies(currencies)
    }

    override fun onCleared() {
        super.onCleared()
        currencyRepository.cancelFetching()
    }
}