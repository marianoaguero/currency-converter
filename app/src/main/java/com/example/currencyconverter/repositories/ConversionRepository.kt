package com.example.currencyconverter.repositories

import com.example.currencyconverter.models.Currency
import com.example.currencyconverter.models.HistoricalConversion
import com.example.currencyconverter.network.RepositoryResponse

interface ConversionRepository {
    suspend fun getConversionsHistory(): List<HistoricalConversion>
    suspend fun convertCurrency(amount: Double, from: Currency, to: Currency): RepositoryResponse<HistoricalConversion>
    suspend fun getSupportedCurrencies(): RepositoryResponse<List<Currency>>
}