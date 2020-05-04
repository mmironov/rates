package com.mmironov.rates.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mmironov.rates.RatesApp

@Database(entities = [Currency::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao

    companion object {
        @Volatile private var instance: CurrencyDatabase? = null
        private val LOCK = Any()

        fun get() = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase().also { instance = it }
        }

        private fun buildDatabase() =
            Room.databaseBuilder(RatesApp.context,
                CurrencyDatabase::class.java,
                "currencies.db")
                .build()
    }
}