package com.example.currencyconverter.repositories

import com.example.currencyconverter.dao.CurrencyDao
import com.example.currencyconverter.models.Currency
import com.example.currencyconverter.network.CurrencyApiService
import jakarta.inject.Inject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class ConversionRepositoryImpl @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val currencyApiService: CurrencyApiService,
) : ConversionRepository {
    override suspend fun getConversionsHistory(): List<Pair<String, Double>> {
        TODO("Not yet implemented")
    }

    override suspend fun convertCurrency(
        amount: Double,
        from: Currency,
        to: Currency
    ): Double? {
        val response = currencyApiService.convertCurrency(
            amount = amount,
            from = from.code,
            to = to.code
        )
        if (response.isSuccessful) {
            val conversionResult = response.body()
            if (conversionResult != null) {
                return conversionResult.result
            } else {
                Timber.d("No conversion result found in response")
            }
        } else {
            Timber.d("Error fetching conversion: ${response.errorBody()}")
        }
        return null
    }

    override suspend fun getSupportedCurrencies(): List<Currency> {
        currencyDao.getAllCurrencies().ifEmpty {
            // Fetch from API and insert into DB
            getCurrenciesFromAPI()
            emptyList()
        }.let {
            return it
        }
    }

    private suspend fun getCurrenciesFromAPI() {
        val response = currencyApiService.getSupportedCurrencies()
        if (response.isSuccessful) {
            val currencies = response.body()
            if (currencies != null) {
                // Insert currencies into the database
                GlobalScope.launch {
                    currencyDao.insertCurrencies(currencies.currencies.map {
                        Currency(it.key, it.value)
                    })
                }
            } else {
                Timber.d("No currencies found in response")
            }
        } else {

            Timber.d("Error fetching currencies: ${response.errorBody()}")
        }
    }
}