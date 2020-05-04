package com.mmironov.rates.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import retrofit2.await
import java.net.ConnectException
import javax.inject.Inject

class CurrencyNetworkDataSource @Inject constructor(
    private val currencyRatesService: CurrencyRatesService
) : CurrencyDataSource {
    private val _latestRates = MutableLiveData<CurrencyRatesResponse>()

    override val latestRates: LiveData<CurrencyRatesResponse>
        get() = _latestRates

    override suspend fun fetchRates(interval: Long, baseCurrency: String): Job = GlobalScope.launch {
        while (isActive) {
            try {
                val latestRates = currencyRatesService.getLatestRatesAsync(baseCurrency).await()
                Log.d(
                    "NetworkDataSource",
                    "from ${Thread.currentThread()}: $latestRates"
                )
                _latestRates.postValue(latestRates)
            } catch(e: ConnectivityException) {
                Log.e(
                    "Connectivity Exception",
                    "No connection",
                    e
                )
            } catch (e: ConnectException) {
                Log.e(
                    "Server Error",
                    "No connection",
                    e
                )
            }
            delay(interval)
        }
    }
}
