package com.practicum.mateocare.profile.data.firebase

import kotlin.reflect.full.memberProperties

fun Any.toMap(): Map<String, Any> {
    return this::class.memberProperties.associate { property ->
        property.name to (property.getter.call(this) ?: "")
    }
}