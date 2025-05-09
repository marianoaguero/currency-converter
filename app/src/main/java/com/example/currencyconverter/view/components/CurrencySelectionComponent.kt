package com.example.currencyconverter.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.R
import com.example.currencyconverter.models.Currency

@Composable
fun CurrencySelectionComponent(
    modifier: Modifier = Modifier,
    currenciesList: List<Currency>,
    onSelection: (Currency) -> Unit,
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var selectedCurrency by remember { mutableStateOf<Currency?>(null) }

    Box(modifier = modifier.fillMaxWidth()) {
        Button(
            onClick = { isDropdownExpanded = true },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = selectedCurrency?.name ?: stringResource(R.string.select_currency),
                    modifier = Modifier.weight(1f),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon",
                    tint = Color.White
                )
            }
        }
        DropdownMenu(
            expanded = isDropdownExpanded,
            onDismissRequest = { isDropdownExpanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            currenciesList.forEach { currency ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = currency.name,
                            color = Color.White
                        )
                    },
                    onClick = {
                        selectedCurrency = currency
                        isDropdownExpanded = false
                        onSelection(currency)
                    },
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencySelectionComponentPreview() {
    val currenciesList = listOf(
        Currency("USD", "United States Dollar"),
        Currency("ARS", "Argentine Peso"),
    )
    CurrencySelectionComponent(currenciesList = currenciesList) { currency ->
        // Handle click
    }
}