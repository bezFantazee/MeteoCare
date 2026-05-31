package com.practicum.mateocare.ui.navigation

import kotlinx.serialization.Serializable

sealed interface NavRoute {
    @Serializable
    object Forecast : NavRoute

    @Serializable
    object Profile : NavRoute
}