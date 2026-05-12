package com.practicum.mateocare.profile.domain.models

data class MeteoStatus(
    override val id: String = "",
    val type: String ="",
    val level: String = "",
    val score: Int = 0,
    val updatedAt: Long = System.currentTimeMillis()
) : Identifiable {
    override fun withId(id: String) = this.copy(id = id)
}