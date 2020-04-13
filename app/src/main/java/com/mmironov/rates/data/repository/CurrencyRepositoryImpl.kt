package com.mmironov.rates.data.repository

import androidx.lifecycle.LiveData
import com.mmironov.rates.data.CurrencyRatesResponse
import com.mmironov.rates.data.network.CurrencyDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyDataSource: CurrencyDataSource
) : CurrencyRepository {
    override suspend fun getRates(baseCurrency: String): LiveData<CurrencyRatesResponse> {
        return withContext(Dispatchers.Default) {
            currencyDataSource.fetchRates(baseCurrency)
            return@withContext currencyDataSource.latestRates
        }
    }
}