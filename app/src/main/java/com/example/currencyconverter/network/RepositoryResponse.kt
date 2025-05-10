package com.example.currencyconverter.network

data class RepositoryResponse<T : Any>(
    val success: Boolean,
    val data: T? = null,
    val error: String? = null,
)
