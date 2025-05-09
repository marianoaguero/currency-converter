package com.example.currencyconverter.dao

import androidx.room.TypeConverter
import com.example.currencyconverter.models.Currency

class CurrencyTypeConverter {
    @TypeConverter
    fun fromCurrency(currency: Currency): String {
        return "${currency.code},${currency.name}" // Serialize to a single string
    }

    @TypeConverter
    fun toCurrency(data: String): Currency {
        val parts = data.split(",")
        return Currency(parts[0], parts[1]) // Deserialize back to Currency
    }
}