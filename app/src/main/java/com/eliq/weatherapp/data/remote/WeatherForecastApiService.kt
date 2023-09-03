package com.eliq.weatherapp.data.remote

import com.eliq.weatherapp.models.WeatherForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastApiService {
    companion object {
        const val BASE_URL = "https://api.open-meteo.com/"
    }

    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double,
        @Query("hourly") hourly: String = "temperature_2m,relativehumidity_2m,rain,windspeed_10m",
    ): Response<WeatherForecastResponse>
}