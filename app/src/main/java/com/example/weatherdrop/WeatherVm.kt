package com.example.weatherdrop

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherdrop.api.Key
import com.example.weatherdrop.api.NetworkResponse
import com.example.weatherdrop.api.RetrofitInstance
import com.example.weatherdrop.models.CitySuggestion
import com.example.weatherdrop.models.WeatherModel
import kotlinx.coroutines.launch
import okio.IOException
import java.net.SocketTimeoutException

class WeatherVm : ViewModel() {
    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherData = MutableLiveData< NetworkResponse<WeatherModel>>()
    private val _citySuggestions = mutableStateOf<List<CitySuggestion>>(emptyList())
    val weatherData: LiveData<NetworkResponse<WeatherModel>> = _weatherData
    val citySuggestions: State<List<CitySuggestion>> = _citySuggestions


    fun getData(city: String){
        _weatherData.value = NetworkResponse.Loading
        viewModelScope.launch {

            try {


                val response = weatherApi.getWeather(Key.apikey, city)

                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherData.value = NetworkResponse.Success(it)
                    } ?: run {
                        _weatherData.value =
                            NetworkResponse.Error("Empty response from the server.")
                    }
                } else {
                    val message = when (response.code()) {
                        400 -> "Bad Request"
                        401 -> "Unauthorized"
                        403 -> "Forbidden"
                        404 -> "Not Found"
                        else -> "Server Error: ${response.code()}"
                    }
                    _weatherData.value = NetworkResponse.Error(message)

                }
            } catch (e: IOException) {
                _weatherData.value = NetworkResponse.Error("No Internet connection")
            }
            catch (e: SocketTimeoutException){
                _weatherData.value = NetworkResponse.Error("Connection Timeout")
            }
            catch (e: Exception) {
                _weatherData.value = NetworkResponse.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun getCitySuggestions(query: String) {
        viewModelScope.launch {
            try {
                val response = weatherApi.getCitySuggestions(Key.apikey, query)
                if (response.isSuccessful && response.body() != null) {
                    _citySuggestions.value = response.body()!!
                }
                else {
                    _citySuggestions.value = emptyList()
                }
            } catch (e : Exception) {
                    _citySuggestions.value = emptyList()
            }
        }
    }

    fun clearSuggestions() {
        _citySuggestions.value = emptyList()
    }
}