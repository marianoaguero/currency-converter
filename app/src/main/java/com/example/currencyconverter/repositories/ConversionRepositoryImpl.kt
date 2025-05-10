package com.example.currencyconverter.repositories

import com.example.currencyconverter.dao.CurrencyDao
import com.example.currencyconverter.dao.HistoricalConversionDao
import com.example.currencyconverter.models.Currency
import com.example.currencyconverter.models.HistoricalConversion
import com.example.currencyconverter.network.CurrencyApiService
import com.example.currencyconverter.network.RepositoryResponse
import jakarta.inject.Inject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

class ConversionRepositoryImpl @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val historicalConversionDao: HistoricalConversionDao,
    private val currencyApiService: CurrencyApiService,
) : ConversionRepository {
    override suspend fun getConversionsHistory(): List<HistoricalConversion> =
        historicalConversionDao.getAllHistoricalConversions()

    override suspend fun convertCurrency(
        amount: Double,
        from: Currency,
        to: Currency
    ): RepositoryResponse<HistoricalConversion> {
        val response = currencyApiService.convertCurrency(
            amount = amount,
            from = from.code,
            to = to.code
        )
        var returnValue = RepositoryResponse<HistoricalConversion>(
            success = false,
            error = "Error fetching conversion"
        )
        if (response.isSuccessful) {
            val conversionResult = response.body()
            if (conversionResult != null && conversionResult.success) {
                returnValue = returnValue.copy(
                    success = true,
                    data = HistoricalConversion(
                        amount = amount,
                        fromCurrency = from,
                        toCurrency = to,
                        convertedAmount = conversionResult.result,
                        timestamp = conversionResult.info.timestamp,
                        conversionRate = conversionResult.info.quote,
                    )
                )
            } else {
                Timber.d("No conversion result found in response")
                returnValue = returnValue.copy(
                    success = false,
                    error = "No conversion result found"
                )
            }
        } else {
            Timber.d("Error fetching conversion: ${response.errorBody()}")
            returnValue = returnValue.copy(
                success = false,
                error = "Error fetching conversion"
            )
        }
        return returnValue
    }

    override suspend fun getSupportedCurrencies(): RepositoryResponse<List<Currency>> {
             val currencyList = currencyDao.getAllCurrencies()
             return if (currencyList.isEmpty()) {
                 Timber.d("No currencies found in local database")
                 getCurrenciesFromAPI()
             } else {
                 Timber.d("Currencies found in local database")
                 RepositoryResponse(success = true, data = currencyList)
             }
         }

    private suspend fun getCurrenciesFromAPI(): RepositoryResponse<List<Currency>> {
        val response = currencyApiService.getSupportedCurrencies()
        var returnValue = RepositoryResponse<List<Currency>>(
            success = false,
            error = "Error fetching currencies"
        )
        if (response.isSuccessful) {
            val currencies = response.body()
            if (currencies != null) {
                returnValue = returnValue.copy(
                    success = true,
                    data = currencies.currencies.map {
                        Currency(it.key, it.value)
                    }
                )
            } else {
                Timber.d("No currencies found in response")
                returnValue = returnValue.copy(
                    success = false,
                    error = "No currencies found"
                )
            }
        } else {
            Timber.d("Error fetching currencies: ${response.errorBody()}")
            returnValue = returnValue.copy(
                success = false,
                error = "Error fetching currencies"
            )
        }
        return returnValue
    }
}