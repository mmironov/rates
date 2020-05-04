package com.mmironov.rates.data.network

import com.google.gson.annotations.SerializedName

data class CurrencyRatesResponse(
    @SerializedName("baseCurrency")
    val baseCurrency: String,

    @SerializedName("rates")
    val rates: Map<String, Double>
)