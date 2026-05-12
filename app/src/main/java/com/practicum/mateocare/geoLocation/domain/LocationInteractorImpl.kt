package com.practicum.mateocare.geoLocation.domain

import android.location.Location
import kotlinx.coroutines.flow.Flow

class LocationInteractorImpl(
    private val repository: LocationRepository
) : LocationInteractor{
    override fun getLocationUpdates(interval: Long): Flow<Location> {
        return repository.getLocationUpdate(interval)
    }
}