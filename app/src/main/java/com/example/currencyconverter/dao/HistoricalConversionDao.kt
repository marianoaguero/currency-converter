package com.example.currencyconverter.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyconverter.models.HistoricalConversion

@Dao
interface HistoricalConversionDao {
    @Query("SELECT * FROM HistoricalConversion ORDER BY timestamp DESC")
    suspend fun getAllHistoricalConversions(): List<HistoricalConversion>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoricalConversion(conversion: HistoricalConversion)

    @Query("DELETE FROM HistoricalConversion WHERE id = :id")
    suspend fun deleteHistoricalConversionById(id: Long)

    @Query("DELETE FROM HistoricalConversion")
    suspend fun clearAllHistoricalConversions()
}