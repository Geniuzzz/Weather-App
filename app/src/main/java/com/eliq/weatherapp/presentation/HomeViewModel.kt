package com.eliq.weatherapp.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eliq.weatherapp.data.remote.GeocodingApiService
import com.eliq.weatherapp.data.remote.WeatherForecastApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiService: GeocodingApiService,
    private val weatherForecastApiService: WeatherForecastApiService
) : ViewModel() {

    private val _weatherInfoState: MutableState<HomeScreenUIModel> = mutableStateOf(
        HomeScreenUIModel(
            "--", null, null, null, null, null
        )
    )
    val weatherInfoState: State<HomeScreenUIModel> = _weatherInfoState

    init {
        fetchWeather()
    }

    fun fetchWeather() {
        viewModelScope.launch {
            try {
                val res = weatherForecastApiService.getForecast(
                    5.05127, 7.9335
                )
                Log.d("XXXX", "$res")
                if (res.isSuccessful) {
                    res.body()?.let {
                        Log.d("XXXX RESPONSE", "$it")
                        _weatherInfoState.value = HomeScreenUIModel.fromAPIResponse(it)
                    }
                }
            } catch (e: Exception) {
                Log.e("XXXXX", e.message, e)
            }
        }
    }
}