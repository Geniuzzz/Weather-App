package com.eliq.weatherapp.models

data class GeocodingResults(
    val results: List<GeocodeResult>
)

data class GeocodeResult(
    val longitude: Double,
    val latitude: Double,
    val name: String,
    val country: String,
    val admin1: String?
) {

    fun getDisplayName(): String {
        val sb = StringBuilder()
        sb.append(name)
        admin1?.takeIf { it.isNotEmpty() }?.let {
            sb.append(",$it")
        }
        country.takeIf { it.isNotEmpty() }?.let {
            sb.append(",$it")
        }
        return sb.toString()
    }
}