package com.practicum.mateocare.ui.forecast.models

data class WeatherParameterUiModel(
    val id: Int,
    val name: String,
    val value: String,
    val unit: String,
    val iconRes: Int? = null,
    val details: List<Any>? = null,
    val time: String? = null
)
