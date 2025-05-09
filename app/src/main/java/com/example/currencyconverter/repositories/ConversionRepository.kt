package com.example.currencyconverter.repositories

import com.example.currencyconverter.models.Currency
import com.example.currencyconverter.models.HistoricalConversion

interface ConversionRepository {
    suspend fun getConversionsHistory(): List<HistoricalConversion>
    suspend fun convertCurrency(amount: Double, from: Currency, to: Currency): Double?
    suspend fun getSupportedCurrencies(): List<Currency>
}