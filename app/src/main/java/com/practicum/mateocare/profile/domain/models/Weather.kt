package com.practicum.mateocare.profile.domain.models

data class Weather(
    override val id: String = "",
    val temp: Int = 0,
    val pressure: Int = 0,
    val humidity: Int = 0,
    val windSpeed: Double = 0.0,
    val condition: String = ""
) : Identifiable {
    override fun withId(id: String) = this.copy(id = id)
}
