package com.eliq.weatherapp.di

import com.eliq.weatherapp.data.remote.GeocodingApiService
import com.eliq.weatherapp.data.remote.WeatherForecastApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ActivityComponent::class)
object WeatherAppModule {

    @Provides
    fun providesWeatherForecastService(): WeatherForecastApiService {
        return Retrofit.Builder()
            .baseUrl(WeatherForecastApiService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(WeatherForecastApiService::class.java)
    }

    @Provides
    fun providesGeocodingApiService(): GeocodingApiService {
        return Retrofit.Builder()
            .baseUrl(GeocodingApiService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(GeocodingApiService::class.java)
    }
}