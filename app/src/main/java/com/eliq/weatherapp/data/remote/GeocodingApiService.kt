package com.eliq.weatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {
    //https://geocoding-api.open-meteo.com/v1/search?name=Berlin&count=10&language=en&format=json
    companion object{
        const val BASE_URL =  "https://geocoding-api.open-meteo.com/"
    }

    @GET("v1/search")
    fun searchLocation(@Query("name") locationName: String, @Query("count") count: Int = 10)
}