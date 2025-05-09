package com.example.currencyconverter.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.dao.CurrencyDao
import com.example.currencyconverter.models.Currency
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
) : ViewModel() {

    var _uiCurrencyState = MutableStateFlow(CurrencyUiState())
    val uiCurrencyState: StateFlow<CurrencyUiState> = _uiCurrencyState

    init {
        getLocalSupportedCurrencies()
        getSupportedCurrencies()
    }

    fun getSupportedCurrencies() {
        // Call the repository method to fetch supported currencies
        viewModelScope.launch(Dispatchers.IO) {
            _uiCurrencyState.value = _uiCurrencyState.value.copy(
                isLoading = false,
                currencies = conversionRepository.getSupportedCurrencies()
            )
        }
    }

    fun convertCurrency(amount: Double, from: Currency, to: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            val conversionResult = conversionRepository.convertCurrency(amount, from, to)
            _uiCurrencyState.value = _uiCurrencyState.value.copy(
                conversionResult = conversionResult
            )
        }
    }

    fun getConversionsHistory() {
        viewModelScope.launch(Dispatchers.IO) {
        }
    }

    fun getLocalSupportedCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiCurrencyState.value = _uiCurrencyState.value.copy(
                isLoading = false,
                currencies = currencyDao.getAllCurrencies()
            )
        }
    }
}

data class CurrencyUiState(
    val currencies: List<Currency> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val conversionResult : Double? = null,
)