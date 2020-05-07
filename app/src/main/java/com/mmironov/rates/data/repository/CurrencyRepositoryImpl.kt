package com.mmironov.rates.data.repository

import androidx.lifecycle.LiveData
import com.mmironov.rates.data.network.CurrencyRatesResponse
import com.mmironov.rates.data.db.Currency
import com.mmironov.rates.data.db.CurrencyDatabase
import com.mmironov.rates.data.network.CurrencyDataSource
import kotlinx.coroutines.*
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyDataSource: CurrencyDataSource
) : CurrencyRepository {
    private var job: Job? = null
    private val currencyDao = CurrencyDatabase.get().currencyDao()

    init {
        currencyDataSource.latestRates.observeForever {
            persistInDb(it)
        }
    }

    override suspend fun getRates(): LiveData<List<Currency>> {
        return withContext(Dispatchers.IO) {
            initCurrencies(currencyDao.getBaseCurrency()?.code ?: "EUR")
            return@withContext currencyDao.listAll()
        }
    }

    private suspend fun initCurrencies(baseCurrency: String) {
        job?.cancel()
        job = currencyDataSource.fetchRates(baseCurrency = baseCurrency)
    }

    private fun persistInDb(currencyRatesResponse: CurrencyRatesResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            var count = currencyDao.getCount()
            currencyRatesResponse.toCurrencies().forEach {
                if (currencyDao.updateRate(it.rate, it.code) < 1) {
                    currencyDao.insert(it.copy(index = count))
                    ++count
                }
            }
        }
    }

    override fun cancelFetching() {
        job?.cancel()
    }

    override val isFetching
        get() = if (job != null) !(job!!.isCancelled) else false

    override fun getBaseCurrency() = currencyDao.getBaseCurrency()

    override fun saveCurrencies(currencies: List<Currency>) {
        currencyDao.insert(*currencies.toTypedArray())
    }
}

fun CurrencyRatesResponse.toCurrencies() = arrayOf(Currency(baseCurrency, 1.0)) + rates.map { Currency(it.key, it.value) }