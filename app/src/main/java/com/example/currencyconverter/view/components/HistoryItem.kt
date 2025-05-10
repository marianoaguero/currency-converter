package com.example.currencyconverter.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverter.models.Currency
import com.example.currencyconverter.models.HistoricalConversion
import com.example.currencyconverter.R
import com.example.currencyconverter.view.theme.CurrencyConverterTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryItem(conversion: HistoricalConversion, modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp)
    ) {
        val (amount, icon, convertedAmount, quote, date) = createRefs()

        Text(
            text = "${conversion.amount} ${conversion.fromCurrency.code}",
            modifier = Modifier
                .constrainAs(amount) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(icon.start)
                }
                .padding(start = 8.dp)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            modifier = Modifier
                .constrainAs(icon) {
                    top.linkTo(parent.top)
                    start.linkTo(amount.end)
                    end.linkTo(convertedAmount.start)
                }
                .padding(start = 8.dp)
        )
        Text(
            text = "${conversion.convertedAmount} ${conversion.toCurrency.code}",
            modifier = Modifier
                .constrainAs(convertedAmount) {
                    top.linkTo(parent.top)
                    start.linkTo(icon.end)
                    end.linkTo(parent.end)
                }
                .padding(start = 8.dp)
        )
        Text(
            text = stringResource(R.string.conversion_rate, conversion.conversionRate),
            modifier = Modifier
                .constrainAs(quote) {
                    top.linkTo(amount.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(date.start)
                }
                .padding(top = 8.dp)
        )
        Text(
            text = stringResource(
                R.string.conversion_time,
                SimpleDateFormat(
                    "yy-MM-dd",
                    Locale.getDefault()
                ).format(Date(conversion.timestamp * 1000))
            ),
            modifier = Modifier
                .constrainAs(date) {
                    top.linkTo(quote.top)
                    start.linkTo(quote.end)
                    end.linkTo(parent.end)
                }
                .padding(top = 8.dp)
        )
    }
}


@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun LightModeHistoryItemPreview() {
    CurrencyConverterTheme(darkTheme = false) {
        HistoryItem(
            conversion = HistoricalConversion(
                amount = 100.0,
                fromCurrency = Currency("USD", "United States Dollar"),
                toCurrency = Currency("EUR", "Euro"),
                convertedAmount = 85.0,
                timestamp = 1746831134,
                conversionRate = 0.85,
            )
        )
    }
}

@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DarkModeHistoryItemPreview() {
    CurrencyConverterTheme(darkTheme = true) {
        HistoryItem(
            conversion = HistoricalConversion(
                amount = 100.0,
                fromCurrency = Currency("USD", "United States Dollar"),
                toCurrency = Currency("EUR", "Euro"),
                convertedAmount = 85.0,
                timestamp = 1746831134,
                conversionRate = 0.85,
            )
        )
    }
}