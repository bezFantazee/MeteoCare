package com.practicum.mateocare.ui.feature.profile.components.meteoStatus

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.practicum.mateocare.profile.domain.models.UserDailyStatus
import com.practicum.mateocare.ui.components.text.BodyText
import com.practicum.mateocare.ui.feature.components.dailyHealth.DailyHealth
import com.practicum.mateocare.ui.feature.components.dailyHealth.DailyHealthStatus
import kotlin.collections.forEach

@Composable
fun StatusHistoryRow(
    statusHistory: List<UserDailyStatus>?,
) {

    if (statusHistory?.isNotEmpty() == true) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            BodyText(
                text = "История самочувствия: "
            )
            statusHistory.forEach { status ->
                Log.d("1we3", status.status)
                DailyHealth(status.status)
            }
        }
    }
}