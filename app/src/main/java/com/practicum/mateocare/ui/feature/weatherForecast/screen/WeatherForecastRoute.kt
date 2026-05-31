package com.practicum.mateocare.ui.feature.weatherForecast.screen

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.practicum.mateocare.ui.feature.weatherForecast.presentation.WeatherForecastScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherForecastRoute(
    modifier: Modifier = Modifier
) {
    val viewModel: WeatherForecastScreenViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsState()

    RequestLocationPermission(
        onGranted = viewModel::onPermissionGranted
    )

    WeatherForecastScreen(
        state = state,
        getDailyAdviceAndStatus = { weatherForecast, answers -> viewModel.loadDailyAdviceAndStatus(weatherForecast, answers) },
        modifier = modifier
    )

}

@Composable
private fun RequestLocationPermission(
    onGranted: () -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
            onGranted()
        }
    }

    LaunchedEffect(Unit) {
        val isGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (isGranted) {
            onGranted()
        } else {
            launcher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
}