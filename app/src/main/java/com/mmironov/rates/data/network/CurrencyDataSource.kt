package com.mmironov.rates.data.network

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Job

interface CurrencyDataSource {
    val latestRates: LiveData<CurrencyRatesResponse>

    suspend fun fetchRates(interval: Long = 1000, baseCurrency: String = "EUR"): Job
}