package com.practicum.mateocare.di

import com.practicum.mateocare.pushMessages.data.NotificationRepositoryImpl
import com.practicum.mateocare.pushMessages.domain.NotificationInteractor
import com.practicum.mateocare.pushMessages.domain.NotificationInteractorImpl
import com.practicum.mateocare.pushMessages.domain.NotificationRepository
import com.practicum.mateocare.ui.feature.profile.presentation.NotificationViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val notificationModule = module {

    single<NotificationRepository> {
        NotificationRepositoryImpl(get())
    }

    single<NotificationInteractor> {
        NotificationInteractorImpl(get())
    }

    viewModelOf(::NotificationViewModel)
}