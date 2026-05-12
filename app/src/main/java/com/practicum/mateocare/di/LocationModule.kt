package com.practicum.mateocare.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.LocationServices
import com.practicum.mateocare.geoLocation.data.FusedLocationProviderClient
import com.practicum.mateocare.geoLocation.data.LocationClient
import com.practicum.mateocare.geoLocation.data.LocationRepositoryImpl
import com.practicum.mateocare.geoLocation.domain.LocationInteractor
import com.practicum.mateocare.geoLocation.domain.LocationInteractorImpl
import com.practicum.mateocare.geoLocation.domain.LocationRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val locationModule = module {
    //data
    single {
        LocationServices.getFusedLocationProviderClient(androidContext())
    }

    single<LocationClient> {
        FusedLocationProviderClient(get())
    }

    //repository
    single<LocationRepository> {
        LocationRepositoryImpl(get())
    }

    //interactor
    single<LocationInteractor>{
        LocationInteractorImpl(get())
    }
}