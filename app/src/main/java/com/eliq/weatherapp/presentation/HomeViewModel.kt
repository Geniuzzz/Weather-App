package com.eliq.weatherapp.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eliq.weatherapp.data.remote.GeocodingApiService
import com.eliq.weatherapp.data.remote.WeatherForecastApiService
import com.eliq.weatherapp.models.GeocodeResult
import com.eliq.weatherapp.models.LocationDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val geocodingApiService: GeocodingApiService,
    private val weatherForecastApiService: WeatherForecastApiService
) : ViewModel() {

    private val _geocodingSuggestions: MutableState<List<GeocodeResult>> =
        mutableStateOf(emptyList())
    val geocodingSuggestions: State<List<GeocodeResult>> = _geocodingSuggestions

    private val _uiInfoState: MutableState<HomeScreenUIModel> = mutableStateOf(HomeScreenUIModel.defaultValues())
    val uiInfoState: State<HomeScreenUIModel> = _uiInfoState


    fun fetchGeocodingResultsFor(name: String) {
        viewModelScope.launch {
            try {
                val response = geocodingApiService.searchLocation(name)
                if (response.isSuccessful) {
                    _geocodingSuggestions.value = response.body()?.results ?: emptyList()
                }
            } catch (e: Exception) {
                Log.e("", e.message, e)
            }
        }
    }

    private fun fetchWeather(geocodeResult: GeocodeResult) {
        viewModelScope.launch {
            try {
                val response = weatherForecastApiService.getForecast(
                    lat = geocodeResult.latitude, lng = geocodeResult.longitude
                )
                if (response.isSuccessful) {
                    response.body()?.let {
                        _uiInfoState.value = HomeScreenUIModel.fromAPIResponse(it).copy(
                            locationName = geocodeResult.getDisplayName()
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("", e.message, e)
            }
        }
    }

    fun onSuggestionSelected(geocodeResult: GeocodeResult) {
        _geocodingSuggestions.value = listOf()
        fetchWeather(geocodeResult)
    }

    fun fetchDataForCurrentLocation(locationDetails: LocationDetails) {
        fetchWeather(
            GeocodeResult(
            longitude = locationDetails.longitude, latitude = locationDetails.latitude, name = "--", "", ""
        )
        )
    }
}