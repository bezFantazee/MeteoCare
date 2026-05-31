package com.practicum.mateocare.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.practicum.mateocare.ui.components.animation.shimmerEffect

@Composable
private fun BaseCard(
    color: Color,
    modifier: Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .background(
                color = color,
                shape = AppCardDefaults.CardShape)
            .padding(AppCardDefaults.ContentPadding),
        content = content
    )
}

@Composable
fun PrimaryCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    BaseCard(
        color = AppCardDefaults.PrimaryColor,
        modifier = modifier,
        content = content
    )
}

@Composable
fun PrimaryCardPlaceholder(
    modifier: Modifier = Modifier,
) {
    BaseCard(
        color = AppCardDefaults.PrimaryColor,
        modifier = modifier,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(20.dp)
                    .clip(shape = AppCardDefaults.ContentShape)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(shape = AppCardDefaults.ContentShape)
                    .shimmerEffect()
            )
        }
    )
}
