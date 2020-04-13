package com.mmironov.rates.dagger

import com.mmironov.rates.ui.CurrenciesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CurrencyModule::class])
interface CurrencyComponent {
    fun inject(fragment: CurrenciesFragment)
}