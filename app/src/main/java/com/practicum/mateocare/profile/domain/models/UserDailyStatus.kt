package com.practicum.mateocare.profile.domain.models


data class UserDailyStatus(
    override val id: String = "",
    val status: String = "",
    val advice: String = "",
    val timestamp: Long = System.currentTimeMillis()
) : Identifiable {
    override fun withId(id: String) = this.copy(id = id)
}