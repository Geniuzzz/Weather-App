package com.eliq.weatherapp.presentation

import com.squareup.moshi.Moshi

object MockData {

    fun getMockUIModel(): HomeScreenUIModel{
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(HomeScreenUIModel::class.java)
        return adapter.fromJson(mockUIModelJson)!!
    }

    private const val mockUIModelJson =
        """{"humidity":"64%","locationName":"Stockholm,\nSweden","rainfall":"3cm","temperature":19.0,"temperatureUnit":"°c","windSpeed":"19km/h"}"""

}