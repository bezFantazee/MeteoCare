package com.practicum.mateocare.ui.components.appBars

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.practicum.mateocare.ui.navigation.BottomDestination
import com.practicum.mateocare.ui.navigation.NavRoute

@Composable
fun MeteoBottomBar(
    currentRoute: NavRoute,
    onNavigate: (NavRoute) -> Unit,
    modifier: Modifier = Modifier
    ) {
    NavigationBar(
        modifier = modifier
    ) {
        BottomDestination.items.forEach { item ->
            //        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            BottomDestination.items.forEach { item ->
//                val isSelected = currentRoute == item.route
//
//                // ИСПРАВЛЕНИЕ МИГАНИЯ: Анимируем прозрачность самого цвета, а не уходим в Color.Transparent
//                val targetIndicatorColor = if (isSelected) {
//                    MaterialTheme.colorScheme.primaryContainer
//                } else {
//                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0f)
//                }
//
//                val indicatorColor by animateColorAsState(
//                    targetValue = targetIndicatorColor,
//                    animationSpec = tween(
//                        durationMillis = 150,
//                        easing = LinearEasing
//                    ),
//                    label = "IndicatorColor"
//                )
//
//                val contentColor by animateColorAsState(
//                    targetValue = if (isSelected) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.primary,
//                    animationSpec = tween(
//                        durationMillis = 150,
//                        easing = LinearEasing
//                    ),
//                    label = "ContentColor"
//                )
//                Box(
//                    modifier = Modifier.weight(1f),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Column(
//                        modifier = Modifier
//
//                            .clip(MaterialTheme.shapes.medium)
//                            .clickable(
//                                interactionSource = remember { MutableInteractionSource() },
//                                indication = null
//                            ) {
//                                onNavigate(item.route)
//                            }
//                            .background(indicatorColor)
//                            .padding(
//                                vertical = AgrohimChatTheme.spacing.xs,
//                                horizontal = AgrohimChatTheme.spacing.sm
//                            ),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.spacedBy(AgrohimChatTheme.spacing.xs),
//                    ) {
//                        Icon(
//                            painter = painterResource(item.iconRes),
//                            contentDescription = item.title,
//                            tint = contentColor,
//                            modifier = Modifier.size(24.dp)
//                        )
//
//                        AnimatedVisibility(
//                            visible = isSelected,
//                            enter = fadeIn(animationSpec = tween(
//                                durationMillis = 150,
//                                easing = FastOutSlowInEasing
//                            )),
//                            exit = slideOutVertically(
//                                animationSpec = tween(
//                                    durationMillis = 150,
//                                    easing = FastOutLinearInEasing
//                                )
//                            ) { it } + fadeOut(animationSpec = tween(
//                                durationMillis = 150,
//                                easing = FastOutLinearInEasing
//                            ))
//                        ) {
//                            Text(
//                                text = item.title,
//                                style = MaterialTheme.typography.labelMedium,
//                                color = contentColor
//                            )
//                        }
//                    }
//                }
//            }
//        }
            BottomBarItem(
                item = item,
                selected = currentRoute == item.route,
                onClick = {
                    onNavigate(item.route)
                }
            )
        }
    }
}

@Composable
fun RowScope.BottomBarItem(
    item: BottomDestination,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,

        icon = {
            Icon(
                painter = painterResource(item.iconRes),
                contentDescription = item.contentDescription,
                modifier = Modifier.size(32.dp)
            )
        },

        label = {
            Text(item.title)
        },

        colors = NavigationBarItemColors(
            unselectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.secondary,
            selectedIconColor = MaterialTheme.colorScheme.inversePrimary,
            selectedTextColor = MaterialTheme.colorScheme.inversePrimary,
            selectedIndicatorColor = MaterialTheme.colorScheme.primaryContainer,
            disabledIconColor = MaterialTheme.colorScheme.error,
            disabledTextColor = MaterialTheme.colorScheme.error
        )
    )
}