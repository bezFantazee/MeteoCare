package com.practicum.mateocare

import android.app.Application
import com.practicum.mateocare.di.aiModule
import com.practicum.mateocare.di.dailyAdviceModule
import com.practicum.mateocare.di.locationModule
import com.practicum.mateocare.di.notificationModule
import com.practicum.mateocare.di.profileModule
import com.practicum.mateocare.di.weatherForecastModule
import com.practicum.mateocare.pushMessages.data.NotificationHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                weatherForecastModule,
                locationModule,
                profileModule,
                aiModule,
                //sharedModule,
                notificationModule,
                dailyAdviceModule
            )
        }

        NotificationHelper.createChannel(this)
    }
}