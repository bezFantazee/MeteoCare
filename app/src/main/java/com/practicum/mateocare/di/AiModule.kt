package com.practicum.mateocare.di

import com.google.gson.Gson
import com.practicum.mateocare.ai.data.AiRepositoryImpl
import com.practicum.mateocare.ai.data.GeminiApi
import com.practicum.mateocare.ai.data.GeminiRetrofitService
import com.practicum.mateocare.ai.data.mapper.AiResponseMapper
import com.practicum.mateocare.ai.data.weather.processor.WeatherFeatureExtractor
import com.practicum.mateocare.ai.domain.AiInteractor
import com.practicum.mateocare.ai.domain.AiInteractorImpl
import com.practicum.mateocare.ai.domain.AiRepository
import com.practicum.mateocare.ai.prompts.AdvicePromptBuilder
import com.practicum.mateocare.ai.prompts.PromptBuilder
import com.practicum.mateocare.ai.prompts.StatusPromptBuilder
import com.practicum.mateocare.ai.prompts.model.AdviceInput
import com.practicum.mateocare.ai.prompts.model.StatusInput
import com.practicum.mateocare.forecast.data.NetworkClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val aiModule = module {
    //data
    single {
        WeatherFeatureExtractor()
    }

    single {
        AiResponseMapper()
    }

    single { Gson() }

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())

            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

            .build()
    }

    single<GeminiApi> {
        Retrofit.Builder()
            .baseUrl("https://generativelanguage.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(GeminiApi::class.java)
    }

    single<NetworkClient> (named("groq")){
        GeminiRetrofitService(get())
    }

    //prompts
    single<PromptBuilder<AdviceInput>>(named("advice")) {
        AdvicePromptBuilder()
    }

    single<PromptBuilder<StatusInput>>(named("status")) {
        StatusPromptBuilder()
    }

    //repository
    single<AiRepository> {
        AiRepositoryImpl(get(named("groq")), get())
    }

    //interactor
    single<AiInteractor> {
        AiInteractorImpl(get(), get(named("advice")), get(named("status")), get())
    }

    //viewModel
    //viewModelOf(::AiViewModel)
}