package com.example.currencyconverter.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoricalConversion(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val fromCurrency: Currency,
    val toCurrency: Currency,
    val amount: Double,
    val convertedAmount: Double,
    val timestamp: Long,
    val conversionRate: Double = 0.0,
)
