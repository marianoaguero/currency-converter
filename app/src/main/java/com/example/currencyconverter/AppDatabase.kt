package com.example.currencyconverter

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currencyconverter.dao.CurrencyDao
import com.example.currencyconverter.models.Currency

@Database(entities = [Currency::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}