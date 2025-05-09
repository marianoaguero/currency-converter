package com.example.currencyconverter

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.currencyconverter.dao.CurrencyDao
import com.example.currencyconverter.dao.CurrencyTypeConverter
import com.example.currencyconverter.dao.HistoricalConversionDao
import com.example.currencyconverter.models.Currency
import com.example.currencyconverter.models.HistoricalConversion

@Database(entities = [Currency::class, HistoricalConversion::class], version = 1)
@TypeConverters(CurrencyTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun historicalConversionDao(): HistoricalConversionDao
}