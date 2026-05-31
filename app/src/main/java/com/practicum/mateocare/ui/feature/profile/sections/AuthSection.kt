package com.practicum.mateocare.ui.feature.profile.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.practicum.mateocare.ui.components.buttons.SecondaryButton

@Composable
fun AuthSection(
    onShowLogin: () -> Unit,
    onShowRegister: () -> Unit,
    modifier: Modifier = Modifier){
    Text(
        text = "Вы еще не авторизовались",
        color = MaterialTheme.colorScheme.tertiary,
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier
    )
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .fillMaxWidth()
    ) {
        SecondaryButton(
            text = "Войти",
            onClick = onShowLogin
        )

        SecondaryButton(
            text = "Зарегистрироваться",
            onClick = onShowRegister
        )
    }
}