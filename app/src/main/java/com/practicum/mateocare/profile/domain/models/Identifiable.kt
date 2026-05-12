package com.practicum.mateocare.profile.domain.models

interface Identifiable {
    val id: String
    fun withId(id: String): Any
}