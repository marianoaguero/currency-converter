package com.example.currencyconverter.network

import com.example.currencyconverter.models.ConversionNetworkData
import com.example.currencyconverter.models.CurrenciesNetworkData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("list")
    suspend fun getSupportedCurrencies(): Response<CurrenciesNetworkData>

    @GET("convert")
    suspend fun convertCurrency(
        @Query("amount") amount: Double,
        @Query("from") from: String,
        @Query("to") to: String,
    ): Response<ConversionNetworkData>
}