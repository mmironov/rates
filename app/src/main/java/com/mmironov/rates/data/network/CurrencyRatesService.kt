package com.mmironov.rates.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mmironov.rates.data.CurrencyRatesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://hiring.revolut.codes/api/android/"
private const val LATEST = "latest"

interface CurrencyRatesService {

    @GET(LATEST)
    fun getLatestRatesAsync(@Query("base") baseCurrency: String = "EUR")
            : Deferred<CurrencyRatesResponse>

    companion object {
        operator fun invoke(): CurrencyRatesService =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CurrencyRatesService::class.java)
    }
}