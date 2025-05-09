package com.example.currencyconverter.models

data class ConversionNetworkData(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val result: Double,
    val query: ConversionQuery,
    val info: ConversionInfo,
)

data class ConversionQuery(
    val from: String,
    val to: String,
    val amount: Double
)

data class ConversionInfo(
    val timestamp: Long,
    val quote: Double,
)