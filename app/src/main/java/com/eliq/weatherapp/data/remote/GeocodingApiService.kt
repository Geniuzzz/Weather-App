package com.eliq.weatherapp.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {
    companion object {
        const val BASE_URL = "https://geocoding-api.open-meteo.com/"
    }

    @GET("v1/search")
    suspend fun searchLocation(
        @Query("name") locationName: String,
        @Query("count") count: Int = 10
    ): Response<Any>
}