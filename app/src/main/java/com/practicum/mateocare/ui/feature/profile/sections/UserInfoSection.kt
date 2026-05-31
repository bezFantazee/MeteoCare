package com.practicum.mateocare.ui.feature.profile.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.practicum.mateocare.ui.components.text.BodyText

@Composable
fun UserInfoSection(email: String, age: String, gender: String) {
    BodyText(
        text = "Email: " + email,
        modifier = Modifier
            .fillMaxWidth(),
        align = TextAlign.Start
    )

    BodyText(
        text = "Возраст: " + age,
        modifier = Modifier
            .fillMaxWidth(),
        align = TextAlign.Start
    )

    BodyText(
        text = "Пол: " + gender,
        modifier = Modifier
            .fillMaxWidth(),
        align = TextAlign.Start
    )
}