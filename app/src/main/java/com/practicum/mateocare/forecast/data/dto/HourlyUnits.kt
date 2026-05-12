package com.practicum.mateocare.forecast.data.dto

import com.google.gson.annotations.SerializedName

data class HourlyUnits(
    @SerializedName("apparent_temperature") val apparentTemperature: String?,
    @SerializedName("dewpoint_2m") val dewpoint2m: String?,
    @SerializedName("precipitation") val precipitation: String?,
    @SerializedName("pressure_msl") val pressureMsl: String?,
    @SerializedName("relative_humidity_2m") val relativeHumidity2m: String?,
    @SerializedName("temperature_2m") val temperature2m: String?,
    @SerializedName("time") val time: String?,
    @SerializedName("windspeed_10m") val windspeed10m: String?
)