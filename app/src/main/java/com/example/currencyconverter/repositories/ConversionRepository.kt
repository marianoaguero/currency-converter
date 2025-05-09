package com.example.currencyconverter.repositories

import com.example.currencyconverter.models.Currency

interface ConversionRepository {
    suspend fun getConversionsHistory(): List<Pair<String, Double>>
    suspend fun convertCurrency(amount: Double, from: Currency, to: Currency): Double?
    suspend fun getSupportedCurrencies(): List<Currency>
}