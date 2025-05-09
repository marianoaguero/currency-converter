package com.example.currencyconverter.repositories

        import com.example.currencyconverter.dao.CurrencyDao
        import com.example.currencyconverter.models.Currency
        import com.example.currencyconverter.network.CurrencyApiService
        import jakarta.inject.Inject
        import kotlinx.coroutines.GlobalScope
        import kotlinx.coroutines.launch
        import retrofit2.Call
        import retrofit2.Callback
        import retrofit2.Response
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
            ): Double {
                TODO("Not yet implemented")
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
                val call: Call<List<Currency>> = currencyApiService.getSupportedCurrencies()
                call.enqueue(object : Callback<List<Currency>> {
                    override fun onResponse(
                        call: Call<List<Currency>>,
                        response: Response<List<Currency>>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let { currencies ->
                                // Insert currencies into the database
                                GlobalScope.launch {
                                    currencyDao.insertCurrencies(currencies)
                                }
                            } ?: Timber.d("No currencies found in response")
                        }
                    }

                    override fun onFailure(call: Call<List<Currency>>, t: Throwable) {
                        // Handle failure
                        Timber.d("Error fetching currencies: ${t.message}")
                    }
                })
            }
        }