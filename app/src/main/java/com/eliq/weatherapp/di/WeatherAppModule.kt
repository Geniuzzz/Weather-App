package com.eliq.weatherapp.di

import com.eliq.weatherapp.BuildConfig
import com.eliq.weatherapp.data.remote.GeocodingApiService
import com.eliq.weatherapp.data.remote.WeatherForecastApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherAppModule {

    @Provides
    @Singleton
    fun providesOkHttpClient() = OkHttpClient.Builder()
        .apply {
            if (BuildConfig.DEBUG){
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }
        }
        .build()

    @Provides
    @Singleton
    fun providesWeatherForecastService(okHttpClient: OkHttpClient): WeatherForecastApiService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(WeatherForecastApiService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(WeatherForecastApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesGeocodingApiService(okHttpClient: OkHttpClient): GeocodingApiService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(GeocodingApiService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(GeocodingApiService::class.java)
    }
}