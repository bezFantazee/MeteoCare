package com.practicum.mateocare.ui.feature.components.dailyHealth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DailyHealth(stringHealth: String) {
    when(stringHealth){
        "high" -> {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(color = DailyHealthDefaults.color(DailyHealthStatus.High), shape = CircleShape)
            )
        }
        "medium" -> {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(color = DailyHealthDefaults.color(DailyHealthStatus.Medium), shape = CircleShape)
            )
        }
        "low" -> {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(color = DailyHealthDefaults.color(DailyHealthStatus.Low), shape = CircleShape)
            )
        }
        else -> {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(color = DailyHealthDefaults.color(DailyHealthStatus.Default), shape = CircleShape)
            )
        }
    }
}