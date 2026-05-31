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
        onShowLogin = profileViewModel::showLoginScreen,
        onShowRegister = profileViewModel::showRegisterScreen,
        onBack = profileViewModel::showAuthMenu,
        onRegister = {email, password, age, gender -> profileViewModel.registerUser(email, password, age, gender) },
        onLogin = {email, password -> profileViewModel.loginUser(email, password)},
        logout = profileViewModel::logout,
        generateMeteoStatus = { answers -> profileViewModel.generateMeteoStatus(answers) },
        modifier = modifier
    )
}