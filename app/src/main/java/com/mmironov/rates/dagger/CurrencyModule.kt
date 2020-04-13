package com.mmironov.rates.dagger

import com.mmironov.rates.data.network.CurrencyDataSource
import com.mmironov.rates.data.network.CurrencyNetworkDataSource
import com.mmironov.rates.data.network.CurrencyRatesService
import com.mmironov.rates.data.repository.CurrencyRepository
import com.mmironov.rates.data.repository.CurrencyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class CurrencyModule {

    @Singleton
    @Binds
    abstract fun provideCurrencyDataSource(service: CurrencyNetworkDataSource): CurrencyDataSource

    @Singleton
    @Binds
    abstract fun provideCurrencyRepository(repository: CurrencyRepositoryImpl): CurrencyRepository

    @Module
    companion object CurrencyModule {
        @JvmStatic
        @Singleton
        @Provides
        fun provideCurrencyRatesService(): CurrencyRatesService = CurrencyRatesService()
    }
}