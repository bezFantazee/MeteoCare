package com.practicum.mateocare.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.practicum.mateocare.ui.components.appBar.MeteoBottomBar
import com.practicum.mateocare.ui.components.appBar.TopBar
import com.practicum.mateocare.ui.navigation.MeteoNavGraph
import com.practicum.mateocare.ui.navigation.NavRoute
import org.koin.androidx.compose.koinViewModel
import androidx.navigation.NavDestination.Companion.hasRoute

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = when {
        backStackEntry?.destination?.hasRoute<NavRoute.Profile>() == true -> NavRoute.Profile
        else -> NavRoute.Forecast
    }

    //val sharedVM: SharedSurveyViewModel = koinViewModel()

    Scaffold(
        topBar = { TopBar() },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        bottomBar = {
            MeteoBottomBar(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId){
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            MeteoNavGraph(
                navController = navController
            )
            }
        }
    }
