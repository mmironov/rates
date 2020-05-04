package com.mmironov.rates.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CurrencyDao {

    @Query("SELECT * from Currency order by `index` asc")
    fun listAll(): LiveData<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg currencies: Currency)

    @Query("UPDATE Currency SET `index` = :index WHERE code = :code")
    fun updateIndex(index: Int, code: String)

    @Query("UPDATE Currency SET rate = :rate WHERE code = :code")
    fun updateRate(rate: Double, code: String): Int

    @Query("SELECT count(*) FROM Currency")
    fun getCount(): Int

    @Query("SELECT * FROM Currency order by `index` asc LIMIT 1")
    fun getBaseCurrency(): Currency?
}