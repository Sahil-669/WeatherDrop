package com.example.weatherdrop.models

data class WeatherModel(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)