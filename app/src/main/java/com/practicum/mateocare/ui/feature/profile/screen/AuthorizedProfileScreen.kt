package com.practicum.mateocare.ui.feature.profile.screen

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.practicum.mateocare.ui.components.buttons.SecondaryButton
import com.practicum.mateocare.ui.components.text.TitleText
import com.practicum.mateocare.ui.feature.profile.presentation.MeteoStatusState
import com.practicum.mateocare.ui.feature.profile.presentation.NotificationViewModel
import com.practicum.mateocare.ui.feature.profile.presentation.models.ProfileUi
import com.practicum.mateocare.ui.feature.profile.sections.MeteoStatusSection
import com.practicum.mateocare.ui.feature.profile.sections.UserInfoSection
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthorizedProfileScreen(
    profile: ProfileUi,
    meteoStatusState: MeteoStatusState,
    generateMeteoStatus: (String) -> Unit,
    logout: () -> Unit
) {
    val notificationViewModel: NotificationViewModel = koinViewModel()
    val hasMeteoStatus = meteoStatusState is MeteoStatusState.Content

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && hasMeteoStatus) {

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = {}
        )

        LaunchedEffect(Unit) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    LaunchedEffect(hasMeteoStatus) {
        notificationViewModel.enable(12, 0)
    }

    TitleText(
        text ="Профиль"
    )

    UserInfoSection(profile.email, profile.age.toString(), profile.gender)

    MeteoStatusSection(
        meteoStatusState = meteoStatusState,
        generateMeteoStatus = generateMeteoStatus
    )

    SecondaryButton(
        text = "Выйти",
        onClick = logout
    )
}




