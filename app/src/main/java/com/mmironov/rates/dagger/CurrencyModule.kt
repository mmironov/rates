package com.mmironov.rates.dagger

import com.mmironov.rates.data.network.*
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
    abstract fun provideCurrencyDataSource(service: CurrencyNetworkDataSource)
            : CurrencyDataSource

    @Singleton
    @Binds
    abstract fun provideCurrencyRepository(repository: CurrencyRepositoryImpl)
            : CurrencyRepository

    @Singleton
    @Binds
    abstract fun provideConnectivityInterceptor(connectivityInterceptor: ConnectivityInterceptorImpl)
            : ConnectivityInterceptor

    @Module
    companion object CurrencyModule {
        @JvmStatic
        @Singleton
        @Provides
        fun provideCurrencyRatesService(connectivityInterceptor: ConnectivityInterceptor)
                : CurrencyRatesService = CurrencyRatesService(connectivityInterceptor)
    }
}