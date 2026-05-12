package com.practicum.mateocare.forecast.data.dto

import com.google.gson.annotations.SerializedName

data class Hourly(
    @SerializedName("time") val time: List<String>?,
    @SerializedName("temperature_2m") val temperature2m: List<Double>?,
    @SerializedName("apparent_temperature") val apparentTemperature: List<Double>?,
    @SerializedName("relative_humidity_2m") val relativeHumidity2m: List<Int>?,
    @SerializedName("pressure_msl") val pressureMsl: List<Double>?,
    @SerializedName("windspeed_10m") val windspeed10m: List<Double>?,
    @SerializedName("precipitation") val precipitation: List<Double>?,
    @SerializedName("dewpoint_2m") val dewpoint2m: List<Double>?,
    @SerializedName("weather_code") val weatherCode: List<Int>?,
)