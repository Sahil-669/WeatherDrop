package com.example.weatherdrop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherdrop.ui.screens.SearchScreen
import com.example.weatherdrop.ui.screens.WeatherDisplay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val weatherVm = ViewModelProvider(this)[WeatherVm::class.java]
        setContent {
            val navControl = rememberNavController()
            NavHost(navController = navControl, startDestination = "search") {
                composable("search") {
                    SearchScreen(weatherVm, navControl)
                }
                composable("weather") {
                    WeatherDisplay(weatherVm, navControl)
                }
            }
        }
    }
}


