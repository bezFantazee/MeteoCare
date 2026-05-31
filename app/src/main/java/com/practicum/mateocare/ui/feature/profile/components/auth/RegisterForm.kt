package com.practicum.mateocare.ui.feature.profile.components.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.practicum.mateocare.ui.components.buttons.PrimaryButton
import com.practicum.mateocare.ui.components.buttons.SecondaryButton
import com.practicum.mateocare.ui.components.forms.radionGroupField.RadioButtonHorizontalField
import com.practicum.mateocare.ui.components.forms.textField.AgeTextField
import com.practicum.mateocare.ui.components.forms.textField.EmailTextField
import com.practicum.mateocare.ui.components.forms.textField.PasswordTextField
import com.practicum.mateocare.ui.theme.MeteoCareTheme

@Composable
fun RegisterForm(
    onRegister: (
        email: String,
        password: String,
        age: Int,
        gender: String
    ) -> Unit,
    onBack: () -> Unit
){
    //состояния пользовательского ввода
    var emailTextState by remember { mutableStateOf("") }
    var passwordTextState by remember { mutableStateOf("") }
    var ageTextState by remember { mutableStateOf("") }

    val options = listOf("Мужской", "Женский")
    var selectedOption by remember { mutableStateOf(options[0]) }

    //РЕГИСТРАЦИЯ
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

    AgeTextField(
        value = ageTextState,
        onValueChange = { ageTextState = it },
        modifier = Modifier.padding(16.dp)
    )

    RadioButtonHorizontalField(
        options = options,
        selectedOption = selectedOption,
        onOptionSelected = { selectedOption = it },
        arrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        options.forEach { text ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = text
                )
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { selectedOption = text }
                )
            }
        }
    }

    SecondaryButton(
        text = "Зарегистрироваться",
        onClick = { onRegister(emailTextState, passwordTextState, ageTextState.toIntOrNull() ?: 0, selectedOption) }
    )

    PrimaryButton(
        text = "Назад",
        onClick = onBack
    )
}