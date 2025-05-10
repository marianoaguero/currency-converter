package com.example.currencyconverter.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.dao.CurrencyDao
import com.example.currencyconverter.dao.HistoricalConversionDao
import com.example.currencyconverter.models.Currency
import com.example.currencyconverter.models.HistoricalConversion
import com.example.currencyconverter.repositories.ConversionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val conversionRepository: ConversionRepository,
    private val currencyDao: CurrencyDao,
    private val historicalConversionDao: HistoricalConversionDao,
) : ViewModel() {

    var _uiCurrencyState = MutableStateFlow(CurrencyUiState())
    val uiCurrencyState: StateFlow<CurrencyUiState> = _uiCurrencyState

    var _uiHistoryState = MutableStateFlow(HistoryUiState())
    val uiHistoryState: StateFlow<HistoryUiState> = _uiHistoryState

    init {
        getConversionsHistory()
    }

    fun getSupportedCurrencies() {
        // Call the repository method to fetch supported currencies
        viewModelScope.launch(Dispatchers.IO) {
            val currenciesResult = conversionRepository.getSupportedCurrencies()
            if (currenciesResult.success) {
                // Save currencies to the database
                val currencies = currenciesResult.data
                if (currencies != null) {
                    currencyDao.insertCurrencies(currencies)
                    _uiCurrencyState.value = _uiCurrencyState.value.copy(
                        currencies = currencies,
                        isLoading = false,
                        error = null
                    )
                }
            } else {
                _uiCurrencyState.value = _uiCurrencyState.value.copy(
                    isLoading = false,
                    error = currenciesResult.error
                )
            }
        }
    }

    fun convertCurrency(amount: Double, from: Currency, to: Currency) {
        _uiCurrencyState.value = _uiCurrencyState.value.copy(
            isLoading = true,
            error = null
        )

        viewModelScope.launch(Dispatchers.IO) {
            val conversionResult = conversionRepository.convertCurrency(amount, from, to)
            if (conversionResult.success) {
                // Save conversion result to the database
                val historicalConversion = conversionResult.data
                if (historicalConversion != null) {
                    historicalConversionDao.insertHistoricalConversion(historicalConversion)
                }
            } else {
                _uiCurrencyState.value = _uiCurrencyState.value.copy(
                    isLoading = false,
                    error = conversionResult.error
                )
            }
            _uiCurrencyState.value = _uiCurrencyState.value.copy(
                isLoading = false,
                conversionResult = conversionResult.data?.convertedAmount,
                resultCurrency = conversionResult.data?.toCurrency
            )
        }
    }

    fun getConversionsHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiHistoryState.value = _uiHistoryState.value.copy(
                isLoading = false,
                conversionHistory = conversionRepository.getConversionsHistory()
            )
        }
    }
}

data class CurrencyUiState(
    val currencies: List<Currency> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val conversionResult : Double? = null,
    val resultCurrency: Currency? = null,
)

data class HistoryUiState(
    val conversionHistory: List<HistoricalConversion> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
)