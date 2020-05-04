package com.mmironov.rates.data.repository

import androidx.lifecycle.LiveData
import com.mmironov.rates.data.db.Currency

interface CurrencyRepository {
    suspend fun getRates(): LiveData<List<Currency>>
    val isFetching: Boolean
    fun cancelFetching()
    fun getBaseCurrency(): Currency?
    fun saveCurrencies(currencies: List<Currency>)
}