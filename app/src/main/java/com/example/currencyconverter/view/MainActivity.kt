package com.example.currencyconverter.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverter.view.components.ConvertScreen
import com.example.currencyconverter.view.components.HistoryScreen
import com.example.currencyconverter.view.theme.CurrencyConverterTheme
import com.example.currencyconverter.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyConverterTheme {
                Scaffold { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "convert", modifier = Modifier.padding(innerPadding)) {
                        composable("convert") {
                            ConvertScreen(navController, viewModel.uiCurrencyState) { amount, fromCurrency, toCurrency ->
                                viewModel.convertCurrency(amount, fromCurrency, toCurrency)
                            }
                        }
                        composable("history") {
                            HistoryScreen(navController)
                        }
                    }
                }
            }
        }
    }
}