
# MeteoCare

Android-приложение для отслеживания погодных условий и анализа их возможного влияния на самочувствие пользователя. Приложение получает метеорологические данные через **Open-Meteo API** и использует **Gemini API** для формирования персональных рекомендаций. Проект построен на Clean Architecture и MVVM.

## Скриншоты

<p align="center">
  <img src="screenshots/forecast_screen.png" width="200" alt="Экран прогноза погоды" />
  <img src="screenshots/profile_screen.png" width="200" alt="Профиль пользователя" />
  <img src="screenshots/daily_quiz.png" width="200" alt="Рекомендации AI" />
</p>

## Основные возможности

### Погода и геолокация
- Получение актуальных метеорологических данных через Open-Meteo API (Retrofit).
- Определение местоположения пользователя с помощью Google Play Services Location.
- Отображение температуры, влажности, атмосферного давления, скорости ветра и других погодных показателей.

### Персонализация и AI-анализ
- Создание и хранение пользовательского профиля с помощью Firebase Authentication и Cloud Firestore.
- Анализ погодных условий с учётом информации о пользователе.
- Генерация персональных рекомендаций и оценка возможного влияния погоды на самочувствие через Gemini API.
- Формирование ежедневного статуса и рекомендаций.

### Уведомления
- Автоматическое получение ежедневных уведомлений с помощью WorkManager.
- Настройка уведомлений и управление их состоянием.

## Архитектура

В проекте используется **Clean Architecture** в сочетании с **MVVM**.

Приложение разделено на три основных слоя:

- **Data Layer** — получение погодных данных через Retrofit, работа с геолокацией, Firebase, Gemini API и системными уведомлениями.
- **Domain Layer** — модели предметной области, интерфейсы репозиториев и интеракторы, содержащие бизнес-логику.
- **Presentation Layer** — экраны Jetpack Compose, ViewModel и управление состояниями интерфейса через StateFlow.

Для внедрения зависимостей используется Koin, асинхронные операции выполняются с помощью Kotlin Coroutines и Flow.

Подробнее: [Архитектура приложения](docs/architecture.md).

## Технологии

| Категория | Инструменты |
|---|---|
| Язык и платформа | Kotlin, Android SDK |
| Минимальная версия Android | minSdk 24 |
| UI | Jetpack Compose, Material 3 |
| Навигация | Navigation Compose |
| Архитектура | Clean Architecture, MVVM |
| Управление состоянием | ViewModel, StateFlow |
| Асинхронность | Kotlin Coroutines, Flow |
| Сеть | Retrofit, Gson, OkHttp, REST API |
| Погодные данные | Open-Meteo API |
| AI | Gemini API |
| Авторизация и хранение данных | Firebase Authentication, Cloud Firestore |
| Геолокация | Google Play Services Location |
| Уведомления | WorkManager, NotificationCompat |
| Графики | Vico |
| Dependency Injection | Koin |

## Сборка и запуск

1. Клонируйте репозиторий:

   ```bash
   git clone https://github.com/bezFantazee/MeteoCare.git
   cd MeteoCare
   ```

2. Откройте проект в Android Studio и дождитесь завершения Gradle Sync.

3. Настройте Firebase: добавьте конфигурационный файл `google-services.json` в модуль приложения и настройте необходимые сервисы.

4. Настройте API-ключ Gemini. Не рекомендуется хранить секретные ключи непосредственно в исходном коде или публиковать их в репозитории.

5. Соберите Debug-версию:

   **Linux / macOS**
   ```bash
   ./gradlew assembleDebug
   ```

   **Windows**
   ```bat
   gradlew.bat assembleDebug
   ```

6. Для установки на подключённое устройство или запущенный эмулятор:

   **Linux / macOS**
   ```bash
   ./gradlew installDebug
   ```

   **Windows**
   ```bat
   gradlew.bat installDebug
   ```

## Планы развития

Текущие идеи по развитию приложения собраны в [Roadmap](docs/roadmap.md).
