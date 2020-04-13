package com.mmironov.rates.data.repository

import androidx.lifecycle.LiveData
import com.mmironov.rates.data.CurrencyRatesResponse

interface CurrencyRepository {
    suspend fun getRates(baseCurrency: String = "EUR"): LiveData<CurrencyRatesResponse>
}