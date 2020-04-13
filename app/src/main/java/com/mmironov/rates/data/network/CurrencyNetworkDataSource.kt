package com.mmironov.rates.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mmironov.rates.data.CurrencyRatesResponse
import javax.inject.Inject

class CurrencyNetworkDataSource @Inject constructor(
    private val currencyRatesService: CurrencyRatesService
) : CurrencyDataSource {
    private val _latestRates = MutableLiveData<CurrencyRatesResponse>()

    override val latestRates: LiveData<CurrencyRatesResponse>
        get() = _latestRates

    override suspend fun fetchRates(baseCurrency: String) {
        val latestRates = currencyRatesService.getLatestRatesAsync(baseCurrency).await()
        Log.d("NetworkDataSource", latestRates.toString())
        _latestRates.postValue(latestRates)
    }
}