package com.practicum.mateocare.ui.feature.weatherForecast.components.weatherGrid

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.practicum.mateocare.ui.feature.weatherForecast.presentation.models.WeatherParameterUiModel
import com.practicum.mateocare.ui.feature.weatherForecast.components.weatherCard.WeatherCard

@Composable
fun WeatherGrid(
    pair: List<WeatherParameterUiModel>,
    expandedItemId: Int?,
    onItemClick: (Int) -> Unit,
    onCollapse: () -> Unit,
    ) {
    val expandedItem = pair.firstOrNull { it.id == expandedItemId }

    Column(
        verticalArrangement = Arrangement.spacedBy(WeatherGridDefaults.ItemSpacing),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = WeatherGridDefaults.collapsedSpring()
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(WeatherGridDefaults.GridSpacing),
            modifier = Modifier.fillMaxWidth()
        ) {
            pair.forEach { item ->
               WeatherCollapsedCard(
                   item = item,
                   expandedItemId = expandedItemId,
                   onItemClick = onItemClick,
                   modifier =  Modifier.weight(1f)
               )
            }

            if (pair.size == 1) {
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        WeatherExpandedCard(
            expandedItem = expandedItem,
            onCollapse = onCollapse
        )
    }
}

@Composable
private fun WeatherCollapsedCard(
    item: WeatherParameterUiModel,
    expandedItemId: Int?,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopStart
    ) {
        AnimatedVisibility(
            visible = expandedItemId != item.id,
            enter = fadeIn() + scaleIn() + expandVertically(
                animationSpec = WeatherGridDefaults.collapsedSpring()
            ),
            exit = fadeOut() + scaleOut() + shrinkVertically(
                animationSpec = WeatherGridDefaults.collapsedSpring()
            )
        ) {
            WeatherCard(
                item = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(WeatherGridDefaults.CardAspectRatio)
                    .clickable {
                        onItemClick(item.id)
                    }
            )
        }
    }
}

@Composable
private fun WeatherExpandedCard(
    expandedItem: WeatherParameterUiModel?,
    onCollapse: () -> Unit
) {
    AnimatedVisibility(
        visible = expandedItem != null,
        enter = fadeIn() + slideInVertically(
            initialOffsetY = { it / 3 }
        ) + expandVertically(
            animationSpec = WeatherGridDefaults.expandedSpring()
        ),
        exit = fadeOut() + slideOutVertically(
            targetOffsetY = { -it / 6 }
        ) + shrinkVertically(
            animationSpec = WeatherGridDefaults.expandedSpring()
        )
    ) {
        expandedItem?.let { item ->
            WeatherCard(
                item = item,
                detailItems = expandedItem.details,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onCollapse()
                    }
            )
        }
    }
}