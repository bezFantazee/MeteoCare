package com.practicum.mateocare.ui.navigation

import androidx.annotation.DrawableRes
import com.practicum.mateocare.R

sealed class BottomDestination(
    val route: NavRoute,
    val title: String,
    @DrawableRes val iconRes: Int,
    val contentDescription: String
) {
    data object Forecast : BottomDestination(
        route = NavRoute.Forecast,
        title = "Погода",
        iconRes = R.drawable.ic_weather_forecast,
        contentDescription = "Прогноз погоды"
    )

    data object Profile : BottomDestination(
        route = NavRoute.Profile,
        title = "Профиль",
        iconRes = R.drawable.ic_profile,
        contentDescription = "Профиль"
    )

    companion object {
        val items = listOf(
            Forecast,
            Profile
        )
    }
}