package com.example.weatherdrop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherdrop.ui.screens.SearchScreen
import com.example.weatherdrop.ui.screens.WeatherDisplay
import com.example.weatherdrop.ui.theme.WeatherDropTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val weatherVm = ViewModelProvider(this)[WeatherVm::class.java]
        setContent {
            val navControl = rememberNavController()
            NavHost(navController = navControl, startDestination = "search") {
                composable("search",) {
                    SearchScreen(weatherVm, navControl)
                }
                composable("weather",) {
                    WeatherDisplay(weatherVm)
                }
            }
        }
    }
}


