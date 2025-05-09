package com.example.currencyconverter.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverter.view.components.ConvertScreen
import com.example.currencyconverter.view.components.HistoryScreen
import com.example.currencyconverter.view.theme.CurrencyConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyConverterTheme {
                Scaffold { innerPadding ->
                    AppNavigator(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavigator(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "convert", modifier = modifier) {
        composable("convert") { ConvertScreen(navController) }
        composable("history") { HistoryScreen(navController) }
    }
}