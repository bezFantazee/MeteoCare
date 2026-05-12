package com.practicum.mateocare.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.practicum.mateocare.ui.SharedSurveyViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    viewModelOf(::SharedSurveyViewModel )
}