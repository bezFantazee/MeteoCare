package com.practicum.mateocare.dailyAdvice

import android.util.Log
import com.google.firebase.firestore.firestoreSettings
import com.practicum.mateocare.ai.domain.AiInteractor
import com.practicum.mateocare.ai.domain.models.GetAiResult
import com.practicum.mateocare.dailyAdvice.model.GenerateAdviceResult
import com.practicum.mateocare.forecast.domain.models.WeatherForecast
import com.practicum.mateocare.profile.domain.firebase.ProfileInteractor
import com.practicum.mateocare.profile.domain.models.HealthProfile
import com.practicum.mateocare.profile.domain.models.UserDailyStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.collections.emptyList

class DailyAdviceInteractorImpl(
    private val profileInteractor: ProfileInteractor,
    private val aiInteractor: AiInteractor
) : DailyAdviceInteractor {
    override suspend fun getTodayStatus(userId: String): UserDailyStatus? {
        return try {
            val result = profileInteractor.getUserDailyStatus(userId, getTodayDateId())
            Log.d("d231", result.toString())
            result
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun generateDailyAdvice(
        userId: String,
        forecast: WeatherForecast,
        answers: String
    ): GenerateAdviceResult {
        return try {
            val profile = profileInteractor.getHealthProfile(
                userId
            )

            if(profile == null || profile.meteoStatus == null) {
                return GenerateAdviceResult.NoUserDataError
            }

            val result = aiInteractor.getAdvice(
                age = profile.age,
                meteoStatus = profile.meteoStatus,
                forecast = forecast,
                answers = answers
            )

            val generatedStatus = when(result){
                is GetAiResult.Success -> {
                    UserDailyStatus(
                        id = getTodayDateId(),
                        status = result.data
                            .dailyStatus ?: "default",
                        advice = result.data
                            .advice
                    )
                }
                else -> {
                    null
                }
            }

            val dailyStatus = generatedStatus ?: return GenerateAdviceResult.GenerateError

            profileInteractor.saveUserDailyStatus(
                userId = userId,
                dailyStatus = dailyStatus
            )

            GenerateAdviceResult.Success(dailyStatus)
        } catch(e: Exception){
            GenerateAdviceResult.GenerateError
        }
    }

    private fun getTodayDateId(): String {
        return SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        ).format(Date())
    }

}