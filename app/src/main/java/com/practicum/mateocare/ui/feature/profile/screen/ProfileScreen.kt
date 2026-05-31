package com.practicum.mateocare.ui.feature.profile.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.practicum.mateocare.ui.feature.components.state.ErrorMessage
import com.practicum.mateocare.ui.feature.components.state.LoadingScreen
import com.practicum.mateocare.ui.feature.profile.presentation.ProfileScreenUiState
import com.practicum.mateocare.ui.theme.MeteoCareTheme

@Composable
fun ProfileScreen(
    profileState: ProfileScreenUiState,
    onShowLogin: () -> Unit,
    onShowRegister: () -> Unit,
    onBack: () -> Unit,
    onRegister: (String, String, Int, String) -> Unit,
    onLogin: (String, String) -> Unit,
    logout: () -> Unit,
    generateMeteoStatus: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MeteoCareTheme.spacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(
                topStart = MeteoCareTheme.spacing.xl,
                topEnd = MeteoCareTheme.spacing.xl,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ))
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = MeteoCareTheme.spacing.md)
            .padding(horizontal = MeteoCareTheme.spacing.md)
            .verticalScroll(rememberScrollState())
            .padding(bottom = MeteoCareTheme.spacing.md)
    ) {
        when(profileState) {
            is ProfileScreenUiState.Loading -> {
                LoadingScreen()
            }
            is ProfileScreenUiState.Error -> {
                ErrorMessage(message = profileState.message)
            }
            is ProfileScreenUiState.Unauthorized -> {
                UnauthorizedProfileScreen(
                    subState = profileState,
                    onShowLogin = onShowLogin,
                    onShowRegister = onShowRegister,
                    onRegister = onRegister,
                    onLogin = onLogin,
                    onBack = onBack
                )
            }
            is ProfileScreenUiState.Authorized -> {
                val state = profileState
                AuthorizedProfileScreen(
                    profile = state.profile,
                    meteoStatusState = state.meteoStatus,
                    generateMeteoStatus = generateMeteoStatus,
                    logout = logout
                )
            }
        }
    }
}