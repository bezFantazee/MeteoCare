package com.practicum.mateocare.profile.domain.models

data class DailyLog(
    override val id: String = "",
    val date: String = "",
    val wellbeing: Int = 0,
    val symptoms: List<String> = emptyList(),
    val notes: String = "",
    val weather: Weather? = null,
    val createdAt: Long = System.currentTimeMillis()
) : Identifiable {
    override fun withId(id: String) = this.copy(id = id)
}