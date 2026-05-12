package com.practicum.mateocare.forecast.data.dto

import com.google.gson.annotations.SerializedName

data class WeatherForecastDto(
    @SerializedName("elevation") val elevation: Int?,
    @SerializedName("generationtime_ms") val generationTimeMs: Double?,
    @SerializedName("hourly") val hourly: Hourly?,
    @SerializedName("hourly_units") val hourlyUnits: HourlyUnits?,
    @SerializedName("latitude") val latitude: Double?,
    @SerializedName("longitude") val longitude: Double?,
    @SerializedName("timezone") val timezone: String?,
    @SerializedName("timezone_abbreviation") val timezoneAbbreviation: String?,
    @SerializedName("utc_offset_seconds") val utcOffsetSeconds: Int?
)