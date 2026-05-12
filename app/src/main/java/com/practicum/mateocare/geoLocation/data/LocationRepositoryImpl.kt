package com.practicum.mateocare.geoLocation.data

import android.location.Location
import com.practicum.mateocare.geoLocation.domain.LocationRepository
import kotlinx.coroutines.flow.Flow

class LocationRepositoryImpl(
    private val locationClient: LocationClient
) : LocationRepository {
    override fun getLocationUpdate(interval: Long): Flow<Location> {
        return locationClient.getLocationUpdates(interval)
    }
}