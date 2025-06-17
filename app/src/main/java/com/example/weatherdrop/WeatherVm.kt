package com.example.weatherdrop

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherdrop.api.Key
import com.example.weatherdrop.api.NetworkResponse
import com.example.weatherdrop.api.RetrofitInstance
import com.example.weatherdrop.api.WeatherModel
import kotlinx.coroutines.launch

class WeatherVm : ViewModel() {
    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherData = MutableLiveData< NetworkResponse<WeatherModel>>()
    val weatherData: LiveData<NetworkResponse<WeatherModel>> = _weatherData

    fun getData(city: String){
        _weatherData.value = NetworkResponse.Loading
        viewModelScope.launch {

            val response = weatherApi.getWeather(Key.apikey, city)

            if (response.isSuccessful) {
                response.body()?.let {
                    _weatherData.value = NetworkResponse.Success(it)
                }
            } else {
                _weatherData.value = NetworkResponse.Error("Failed to fetch data")
            }

        }
    }
}