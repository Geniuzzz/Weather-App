package com.eliq.weatherapp.models

data class WeatherForecastResponse(
    val hourly: Hourly?,
    val hourly_units: HourlyUnits?
)

data class Hourly(
    val time: List<String>?,
    val temperature_2m: List<Double>?,
    val relativehumidity_2m: List<Double>?,
    val rain: List<Double>?,
    val windspeed_10m: List<Double>?
)

data class HourlyUnits(
    val time: String?,
    val temperature_2m: String?,
    val relativehumidity_2m: String?,
    val rain: String?,
    val windspeed_10m: String?
)