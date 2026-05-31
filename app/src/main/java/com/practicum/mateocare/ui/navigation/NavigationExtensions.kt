package com.practicum.mateocare.ui.navigation

import androidx.navigation.NavController


fun NavController.navigateSingleTopTo(
    route: String
) {
    navigate(route) {
        popUpTo(graph.startDestinationId) {
            saveState = true
        }

        launchSingleTop = true
        restoreState = true
    }
}