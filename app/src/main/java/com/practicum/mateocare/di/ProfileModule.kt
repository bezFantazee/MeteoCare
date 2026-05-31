package com.practicum.mateocare.di

import com.google.firebase.firestore.FirebaseFirestore
import com.practicum.mateocare.profile.data.firebase.RemoteDatabaseClient
import com.practicum.mateocare.profile.data.firebase.FirebaseFirestoreClient
import com.practicum.mateocare.profile.data.ProfileRepositoryImpl
import com.practicum.mateocare.profile.data.firebase.AuthClient
import com.practicum.mateocare.profile.data.firebase.FirebaseAuthClient
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

    single<RemoteDatabaseClient> {
        FirebaseFirestoreClient(get(), "users")
    }
    single<AuthClient> {
        FirebaseAuthClient()
    }

    //repository
    single<ProfileRepository> {
        ProfileRepositoryImpl(get(), get())
    }

    //interactor
    single<ProfileInteractor> {
        ProfileInteractorImpl(get())
    }

    //viewModel
    viewModelOf(::ProfileViewModel)
}