package com.mmironov.rates.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mmironov.rates.data.repository.CurrencyRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class CurrenciesViewModelFactory @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrenciesViewModel(currencyRepository) as T
    }
}