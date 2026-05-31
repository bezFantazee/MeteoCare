package com.practicum.mateocare.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.practicum.mateocare.ui.feature.profile.screen.ProfileRoute
import com.practicum.mateocare.ui.feature.weatherForecast.screen.WeatherForecastRoute

@Composable
fun MeteoNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Forecast
    ) {
        composable<NavRoute.Forecast> {
            WeatherForecastRoute()
        }

        composable<NavRoute.Profile> {
            ProfileRoute()
        }
    }
}