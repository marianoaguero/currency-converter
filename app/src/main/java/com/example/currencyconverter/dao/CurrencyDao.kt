package com.example.currencyconverter.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyconverter.models.Currency

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currency")
    suspend fun getAllCurrencies(): List<Currency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies: List<Currency>)

    @Query("DELETE FROM currency")
    suspend fun clearCurrencies()
}