package com.example.weatherdrop.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/v1/forecast.json")
    suspend fun getWeather(
        @Query("Key") key : String,
        @Query("q") city : String,
        @Query("days") days : Int = 1
    ) : Response<WeatherModel>

}