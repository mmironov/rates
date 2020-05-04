package com.mmironov.rates.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://hiring.revolut.codes/api/android/"
private const val LATEST = "latest"

interface CurrencyRatesService {

    @GET(LATEST)
    fun getLatestRatesAsync(@Query("base") baseCurrency: String = "EUR")
            : Call<CurrencyRatesResponse>

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): CurrencyRatesService {
            val httpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .build()


            return Retrofit.Builder()
                .client(httpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CurrencyRatesService::class.java)
        }
    }
}