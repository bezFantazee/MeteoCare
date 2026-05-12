package com.practicum.mateocare.geoLocation.domain

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationInteractor {
    fun getLocationUpdates(interval: Long): Flow<Location>
}