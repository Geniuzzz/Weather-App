package com.eliq.weatherapp.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastApiService {
    //https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&hourly=temperature_2m,relativehumidity_2m

    companion object {
        const val BASE_URL = "https://api.open-meteo.com/"
    }

    @GET("v1/forecast")
    suspend fun getForecast(@Query("latitude") lat: Double, @Query("longitude") lng: Double, @Query("hourly") hourly: String = "temperature_2m,relativehumidity_2m"): Response<Any>
}