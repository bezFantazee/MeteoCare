package com.practicum.mateocare.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.practicum.mateocare.R
import com.practicum.mateocare.ui.forecast.screen.ShowWeatherForecast
import com.practicum.mateocare.ui.profile.screen.ProfileScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: NavRoutes.Forecast

    val sharedVM: SharedSurveyViewModel = koinViewModel()

    Scaffold(
        topBar = { TopBar() },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        bottomBar = {
            NavigationBar(
            ) {
                NavigationBarItem(
                    selected = currentRoute == NavRoutes.Forecast,
                    onClick = {
                        navController.navigate(NavRoutes.Forecast) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true}
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(
                        painter = painterResource(R.drawable.ic_weather_forecast),
                        contentDescription = "Прогноз погоды",
                        modifier = Modifier.size(32.dp)
                        )
                           },
                    label = { Text("Погода") },
                    colors = NavigationBarItemColors(
                        unselectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.secondary,
                        selectedIconColor = MaterialTheme.colorScheme.inversePrimary,
                        selectedTextColor = MaterialTheme.colorScheme.inversePrimary,
                        selectedIndicatorColor = MaterialTheme.colorScheme.primaryContainer,
                        disabledIconColor = MaterialTheme.colorScheme.error,
                        disabledTextColor = MaterialTheme.colorScheme.error
                    )
                )

                NavigationBarItem(
                    selected = currentRoute == NavRoutes.Profile,
                    onClick = {
                        navController.navigate(NavRoutes.Profile) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true

                            }
                        },
                    icon = { Icon(
                        painter = painterResource(R.drawable.ic_profile,),
                        contentDescription = "Профиль",
                        modifier = Modifier.size(32.dp)
                        )},
                    label = { Text("Профиль") },
                    colors = NavigationBarItemColors(
                        unselectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.secondary,
                        selectedIconColor = MaterialTheme.colorScheme.inversePrimary,
                        selectedTextColor = MaterialTheme.colorScheme.inversePrimary,
                        selectedIndicatorColor = MaterialTheme.colorScheme.primaryContainer,
                        disabledIconColor = MaterialTheme.colorScheme.error,
                        disabledTextColor = MaterialTheme.colorScheme.error
                    )
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = NavRoutes.Forecast
            ) {
                composable(NavRoutes.Forecast) {
                    ShowWeatherForecast(
                        sharedVM = sharedVM,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(shape = RoundedCornerShape(
                                topStart = 32.dp,
                                topEnd = 32.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                                ))
                            .background(MaterialTheme.colorScheme.surface)
                    )
                }
                composable(NavRoutes.Profile) {
                    ProfileScreen(
                        sharedVM = sharedVM,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(shape = RoundedCornerShape(
                                topStart = 32.dp,
                                topEnd = 32.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            ))
                            .background(MaterialTheme.colorScheme.surface)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(){
    TopAppBar(
        title = {
            Text(
                text = "Meteo Care",
                style = MaterialTheme.typography.headlineLarge
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
    )
}
