package com.practicum.mateocare.di

import com.practicum.mateocare.dailyAdvice.DailyAdviceInteractor
import com.practicum.mateocare.dailyAdvice.DailyAdviceInteractorImpl
import org.koin.dsl.module

val dailyAdviceModule = module {
    //interactor
    single<DailyAdviceInteractor>{
        DailyAdviceInteractorImpl(get(), get())
    }
}