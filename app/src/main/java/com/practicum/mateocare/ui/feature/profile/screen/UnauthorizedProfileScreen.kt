package com.practicum.mateocare.ui.feature.profile.screen

import androidx.compose.runtime.Composable
import com.practicum.mateocare.ui.feature.profile.components.auth.LoginForm
import com.practicum.mateocare.ui.feature.profile.components.auth.RegisterForm
import com.practicum.mateocare.ui.feature.profile.presentation.ProfileScreenUiState
import com.practicum.mateocare.ui.feature.profile.sections.AuthSection

@Composable
fun UnauthorizedProfileScreen(
    subState: ProfileScreenUiState.Unauthorized,
    onShowLogin: () -> Unit,
    onShowRegister: () -> Unit,
    onRegister: (
        email: String,
        password: String,
        age: Int,
        gender: String
    ) -> Unit,
    onLogin: (
        email: String,
        password: String
    ) -> Unit,
    onBack: () -> Unit
) {
    //основной экран выбора аутентификации
    when(subState) {
        is ProfileScreenUiState.Unauthorized.Initial -> {
            AuthSection(onShowLogin, onShowRegister)
        }
        is ProfileScreenUiState.Unauthorized.LoginMode -> {
            LoginForm(onLogin, onBack)
        }
        is ProfileScreenUiState.Unauthorized.RegisterMode -> {
            RegisterForm(onRegister, onBack)
        }
    }
}