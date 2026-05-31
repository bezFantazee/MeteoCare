package com.practicum.mateocare.ui.components.appBar

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