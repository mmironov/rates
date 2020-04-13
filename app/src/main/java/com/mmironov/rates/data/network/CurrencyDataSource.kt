package com.mmironov.rates.data.network

import androidx.lifecycle.LiveData
import com.mmironov.rates.data.CurrencyRatesResponse

interface CurrencyDataSource {
    val latestRates: LiveData<CurrencyRatesResponse>

    suspend fun fetchRates(baseCurrency: String = "EUR")
}