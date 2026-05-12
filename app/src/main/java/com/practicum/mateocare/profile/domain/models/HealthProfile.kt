package com.practicum.mateocare.profile.domain.models

data class HealthProfile(
    override val id: String = "",
    val age: Int = 0,
    val gender: String = "",
    val meteoStatus: MeteoStatus? = null,
    val mainAdvice: String? = null,
    val updateAt: Long = System.currentTimeMillis()
) : Identifiable {
    override fun withId(id: String) = this.copy(id = id)
}
