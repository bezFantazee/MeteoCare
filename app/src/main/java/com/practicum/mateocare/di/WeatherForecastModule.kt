package com.practicum.mateocare.di

import com.google.gson.Gson
import com.practicum.mateocare.forecast.data.GetWeatherForecastRepositoryImpl
import com.practicum.mateocare.forecast.data.NetworkClient
import com.practicum.mateocare.forecast.data.MeteoRetrofitNetworkClient
import com.practicum.mateocare.forecast.data.api.MeteoApiService
import com.practicum.mateocare.forecast.domain.GetWeatherForecastInteractor
import com.practicum.mateocare.forecast.domain.GetWeatherForecastInteractorImpl
import com.practicum.mateocare.forecast.domain.GetWeatherForecastRepository
import com.practicum.mateocare.ui.feature.weatherForecast.presentation.WeatherForecastScreenViewModel
import com.practicum.mateocare.ui.feature.weatherForecast.presentation.mappers.ForecastUiMapper
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val weatherForecastModule = module {
    //data
    single<MeteoApiService> {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MeteoApiService::class.java)
    }

    factory { Gson() }

    single<NetworkClient> (named("meteo")){
        MeteoRetrofitNetworkClient(get())
    }

    //repository
    single<GetWeatherForecastRepository>{
        GetWeatherForecastRepositoryImpl(get(named("meteo")))
    }

    //interactor
    single<GetWeatherForecastInteractor>{
        GetWeatherForecastInteractorImpl(get())
    }

    //viewModel
    single {
        ForecastUiMapper()
    }

    viewModelOf(::WeatherForecastScreenViewModel)
}
