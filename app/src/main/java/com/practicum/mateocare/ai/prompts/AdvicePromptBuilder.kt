package com.practicum.mateocare.ai.prompts

import com.practicum.mateocare.ai.prompts.model.AdviceInput

class AdvicePromptBuilder : PromptBuilder<AdviceInput> {

    override fun build(input: AdviceInput): String {
        return """
Ты медицинско-метеорологический ассистент.

Твоя задача:
1. Проанализировать погодные условия
2. Учесть метеочувствительность пользователя
3. Сформировать краткий практический совет

Верни ТОЛЬКО JSON:

{
  "meteoStatus": {
    "type": "",
    "level": "low | medium | high",
    "score": 0
  },
  
  "dailyStatus": "low | medium | hight"
  
  "advice": ""
}

ЯЗЫК ОТВЕТА:
- отвечай ТОЛЬКО на русском языке
- все текстовые поля (type, advice) на русском
- level строго: low | medium | high
- dailyStatus строго: low | medium | high, содержит оценку сегодняшнего состояния пользователя

ПОЛЬЗОВАТЕЛЬ:
Возраст: ${input.age}

Ответы на опрос про сегодняшнее самочувствие: ${input.answers}

ТЕКУЩИЙ МЕТЕО-СТАТУС:
Тип: ${input.meteoStatus.type}
Уровень: ${input.meteoStatus.level}
Score: ${input.meteoStatus.score}

ПОГОДНЫЕ ФЕАТУРЫ:

Температура:
- средняя: ${input.weather.tempAvg}°C
- минимальная: ${input.weather.tempMin}°C
- максимальная: ${input.weather.tempMax}°C
- амплитуда: ${input.weather.tempDelta}°C

Ощущаемая температура:
- разница ощущаемой и реальной: ${input.weather.feelsLikeDiff}°C

Влажность:
- средняя: ${input.weather.humidityAvg}%
- часы высокой влажности (>80%): ${input.weather.highHumidityHours}

Давление:
- среднее: ${input.weather.pressureAvg} hPa
- перепад давления: ${input.weather.pressureDrop} hPa

Ветер:
- средняя скорость: ${input.weather.windAvg} m/s

Осадки:
- суммарные осадки: ${input.weather.precipitationTotal} mm

ПРАВИЛА ГЕНЕРАЦИИ:
- максимум 2 предложения
- до 150 символов
- конкретные действия
- учитывать резкие изменения погоды (температура/давление)
- учитывать влажность и ветер

ПРИМЕРЫ:
- "Снизьте нагрузку, возможны перепады давления"
- "Избегайте перегрева, высокая влажность"
- "Благоприятные условия для активности"

ЗАПРЕЩЕНО:
- диагнозы
- медицинские утверждения
- длинные объяснения

Верни только JSON:
""".trimIndent()
    }
}