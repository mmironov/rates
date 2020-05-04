package com.mmironov.rates.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Currency(
    @PrimaryKey
    val code: String,
    val rate: Double,
    val index: Int = 0
) {
}