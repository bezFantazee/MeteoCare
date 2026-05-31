package com.practicum.mateocare.ui.feature.profile.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.practicum.mateocare.ui.feature.profile.presentation.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileRoute(modifier: Modifier = Modifier) {
    val profileViewModel: ProfileViewModel = koinViewModel()
    val profileState by profileViewModel.uiState.collectAsState()

    ProfileScreen(
        profileState = profileState,
        onShowLogin = profileViewModel::showLogin,
        onShowRegister = profileViewModel::showRegister,
        onBack = profileViewModel::back,
        onRegister = {email, password, age, gender -> profileViewModel.registerUser(email, password, age, gender) },
        onLogin = {email, password -> profileViewModel.loginUser(email, password)},
        logout = profileViewModel::logout,
        getMeteoStatus = {answers -> profileViewModel.getMeteoStatus(answers) },
        observeWeekUserStatus = profileViewModel::observeStatusHistory,
        modifier = modifier
    )
}