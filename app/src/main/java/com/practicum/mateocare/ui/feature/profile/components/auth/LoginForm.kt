package com.practicum.mateocare.ui.feature.profile.components.auth

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.practicum.mateocare.ui.components.buttons.PrimaryButton
import com.practicum.mateocare.ui.components.buttons.SecondaryButton
import com.practicum.mateocare.ui.components.forms.textField.EmailTextField
import com.practicum.mateocare.ui.components.forms.textField.PasswordTextField
import com.practicum.mateocare.ui.theme.MeteoCareTheme

@Composable
fun LoginForm(
    onLogin: (
        email: String,
        password: String
    ) -> Unit,
    onBack: () -> Unit,
) {
    //состояния пользовательского ввода
    var emailTextState by remember { mutableStateOf("") }
    var passwordTextState by remember { mutableStateOf("") }

    //ВХОД
    EmailTextField(
        value = emailTextState,
        onValueChange = { emailTextState = it },
        modifier = Modifier.padding(MeteoCareTheme.spacing.md)
    )

    PasswordTextField(
        value = passwordTextState,
        onValueChange = { passwordTextState = it },
        modifier = Modifier.padding(MeteoCareTheme.spacing.md)
    )

    SecondaryButton(
        text = "Войти",
        onClick = { onLogin(emailTextState, passwordTextState) }
    )

    PrimaryButton(
        text = "Назад",
        onClick = onBack
    )
}