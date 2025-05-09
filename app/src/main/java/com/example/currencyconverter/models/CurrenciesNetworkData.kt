package com.example.currencyconverter.models

data class CurrenciesNetworkData(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val currencies: Map<String, String>,
)