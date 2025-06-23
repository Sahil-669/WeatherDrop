package com.example.weatherdrop.api

data class WeatherModel(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)