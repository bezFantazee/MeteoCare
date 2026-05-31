package com.practicum.mateocare.di

import com.google.firebase.firestore.FirebaseFirestore
import com.practicum.mateocare.profile.data.firebase.FirebaseClient
import com.practicum.mateocare.profile.data.firebase.FirestoreClient
import com.practicum.mateocare.profile.data.firebase.ProfileRepositoryImpl
import com.practicum.mateocare.profile.domain.firebase.ProfileInteractor
import com.practicum.mateocare.profile.domain.firebase.ProfileInteractorImpl
import com.practicum.mateocare.profile.domain.firebase.ProfileRepository
import com.practicum.mateocare.ui.feature.profile.presentation.ProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val profileModule = module {
    //data
    single {
        FirebaseFirestore.getInstance()
    }

    single<FirebaseClient> {
        FirestoreClient(get(), "users")
    }

    //repository
    single<ProfileRepository> {
        ProfileRepositoryImpl(get())
    }

    //interactor
    single<ProfileInteractor> {
        ProfileInteractorImpl(get())
    }

    //viewModel
    viewModelOf(::ProfileViewModel)
}