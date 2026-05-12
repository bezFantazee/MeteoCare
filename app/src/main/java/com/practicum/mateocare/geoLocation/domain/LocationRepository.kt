package com.practicum.mateocare.geoLocation.domain

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getLocationUpdate(interval: Long): Flow<Location>
}