package com.practicum.mateocare.ai.domain

import com.practicum.mateocare.ai.data.weather.model.WeatherFeatures
import com.practicum.mateocare.ai.data.weather.processor.WeatherFeatureExtractor
import com.practicum.mateocare.ai.domain.models.GetAiResult
import com.practicum.mateocare.ai.prompts.PromptBuilder
import com.practicum.mateocare.ai.prompts.model.AdviceInput
import com.practicum.mateocare.ai.prompts.model.StatusInput
import com.practicum.mateocare.forecast.domain.models.WeatherForecast
import com.practicum.mateocare.profile.domain.models.MeteoStatus
import kotlinx.coroutines.flow.Flow

class AiInteractorImpl(
    private val repository: AiRepository,
    private val advicePromptBuilder: PromptBuilder<AdviceInput>,
    private val statusPromptBuilder: PromptBuilder<StatusInput>,
    private val weatherFeaturesExtractor: WeatherFeatureExtractor
) : AiInteractor {
    override suspend fun getAdvice(age: Int, meteoStatus: MeteoStatus, forecast: WeatherForecast, answers: String): Flow<GetAiResult> {
        val weatherFeature = weatherFeaturesExtractor.extract(forecast)
        val input = AdviceInput(
            age = age,
            meteoStatus = meteoStatus,
            weather = weatherFeature,
            answers = answers
        )

        val prompt = advicePromptBuilder.build(input)
        return repository.generateMeteoAdvice(prompt)
    }

    override suspend fun getMeteoStatus(age: Int, answers: String): Flow<GetAiResult> {
        val input = StatusInput(
            age = age,
            answers = answers
        )
        val prompt = statusPromptBuilder.build(input)
        return repository.generateMeteoAdvice(prompt)
    }
}