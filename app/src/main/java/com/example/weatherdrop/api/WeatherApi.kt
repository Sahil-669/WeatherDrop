package com.example.weatherdrop.api

import com.example.weatherdrop.models.CitySuggestion
import com.example.weatherdrop.models.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/v1/forecast.json")
    suspend fun getWeather(
        @Query("key") key: String,
        @Query("q") city: String,
        @Query("days") days: Int = 1
    ): Response<WeatherModel>

    @GET("/v1/search.json")
    suspend fun getCitySuggestions(
        @Query("key") key: String,
        @Query("q") query: String
    ) : Response<List<CitySuggestion>>
}