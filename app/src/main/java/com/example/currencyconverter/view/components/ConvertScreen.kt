package com.example.currencyconverter.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.currencyconverter.R
import com.example.currencyconverter.models.Currency
import com.example.currencyconverter.view.theme.CurrencyConverterTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConvertScreen(navController: NavHostController) {

    var amount by remember { mutableStateOf("") }
    var selectedFromCurrency by remember { mutableStateOf<Currency?>(null) }
    var selectedToCurrency by remember { mutableStateOf<Currency?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(R.string.currency_converter_title),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            })
        },
        content = { padding ->
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
            ) {
                val (
                    amountField,
                    fromTitle,
                    fromCurrency,
                    toTitle,
                    toCurrency,
                    historyButton,
                    convertButton
                ) = createRefs()


                TextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text(stringResource(R.string.amount)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp)
                        .constrainAs(amountField) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                Text(
                    text = stringResource(R.string.from),
                    modifier = Modifier
                        .constrainAs(fromTitle) {
                            top.linkTo(amountField.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
                CurrencySelectionComponent(
                    currenciesList = listOf(
                        Currency("USD", "United States Dollar"),
                        Currency("ARS", "Argentine Peso")
                    ),
                    onSelection = { currency ->
                        selectedFromCurrency = currency
                    },
                    modifier = Modifier.constrainAs(fromCurrency) {
                        top.linkTo(fromTitle.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
                Text(
                    text = stringResource(R.string.to),
                    modifier = Modifier
                        .constrainAs(toTitle) {
                            top.linkTo(fromCurrency.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
                CurrencySelectionComponent(
                    currenciesList = listOf(
                        Currency("USD", "United States Dollar"),
                        Currency("ARS", "Argentine Peso")
                    ),
                    onSelection = { currency ->
                        selectedToCurrency = currency
                    },
                    modifier = Modifier.constrainAs(toCurrency) {
                        top.linkTo(toTitle.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
                Button(
                    onClick = { navController.navigate("history") },
                    modifier = Modifier
                        .wrapContentSize()
                        .background(Color.Transparent)
                        .padding(16.dp)
                        .constrainAs(convertButton) {
                            top.linkTo(toCurrency.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Text(stringResource(R.string.convert))
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
fun LightModePreview() {
    CurrencyConverterTheme(darkTheme = false) {
        ConvertScreen(navController = NavHostController(LocalContext.current))
    }
}

@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DarkModePreview() {
    CurrencyConverterTheme(darkTheme = true) {
        ConvertScreen(navController = NavHostController(LocalContext.current))
    }
}