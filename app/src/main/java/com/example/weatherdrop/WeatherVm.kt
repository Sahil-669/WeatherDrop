package com.example.weatherdrop

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherdrop.api.Key
import com.example.weatherdrop.api.RetrofitInstance
import kotlinx.coroutines.launch

class WeatherVm : ViewModel() {
    private val weatherApi = RetrofitInstance.weatherApi

    fun getData(city: String){
        viewModelScope.launch {

            val response = weatherApi.getWeather(Key.apikey, city)

            if (response.isSuccessful) {
                Log.i("Response: ", response.body().toString())
            } else {
                Log.i("Response: ", response.message())
            }

        }
    }
}