package com.example.currencyconverter.view.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.currencyconverter.R
import com.example.currencyconverter.models.Currency
import com.example.currencyconverter.models.HistoricalConversion
import com.example.currencyconverter.view.theme.CurrencyConverterTheme
import com.example.currencyconverter.viewModels.HistoryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navController: NavHostController,
    uiState: StateFlow<HistoryUiState>,
    getHistory: () -> Unit
) {
    LaunchedEffect(Unit) {
        // Fetch the conversion history from the ViewModel
        getHistory()
    }

    val context = LocalContext.current
    val uiState = uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.conversion_history_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { padding ->
            if (uiState.value.conversionHistory.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_conversion_history),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .scrollable(rememberScrollState(), Orientation.Vertical)
                    .padding(padding)
                    .padding(16.dp)
            ) {
                items(uiState.value.conversionHistory.size) { index ->
                    HistoryItem(
                        uiState.value.conversionHistory[index],
                        modifier = Modifier.clickable {
                            Toast.makeText(
                                context,
                                "Clicked on ${uiState.value.conversionHistory[index].fromCurrency.code} " +
                                        "${uiState.value.conversionHistory[index].amount} " +
                                        "to ${uiState.value.conversionHistory[index].toCurrency.code}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    )
}


@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun LightModeHistoryPreview() {
    CurrencyConverterTheme(darkTheme = false) {
        HistoryScreen(
            navController = NavHostController(LocalContext.current),
            uiState = MutableStateFlow(HistoryUiState()),
            getHistory = {}
        )
    }
}

@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DarkModeHistoryPreview() {
    CurrencyConverterTheme(darkTheme = true) {
        HistoryScreen(
            navController = NavHostController(LocalContext.current),
            uiState = MutableStateFlow(HistoryUiState()),
            getHistory = {}
        )
    }
}
