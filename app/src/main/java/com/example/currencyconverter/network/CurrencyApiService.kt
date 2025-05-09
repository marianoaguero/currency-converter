package com.example.currencyconverter.network

import com.example.currencyconverter.models.Currency
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyApiService {
    @GET("list")
    suspend fun getSupportedCurrencies(): Call<List<Currency>>
}