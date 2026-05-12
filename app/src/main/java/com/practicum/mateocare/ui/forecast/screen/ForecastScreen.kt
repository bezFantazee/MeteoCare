package com.practicum.mateocare.ui.forecast.screen

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.layer.continuous
import com.patrykandpatrick.vico.compose.cartesian.layer.point
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.common.ProvideVicoTheme
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.shape.toVicoShape
import com.patrykandpatrick.vico.compose.m3.common.rememberM3VicoTheme
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.practicum.mateocare.forecast.domain.models.WeatherForecast
import com.practicum.mateocare.profile.domain.models.UserDailyStatus
import com.practicum.mateocare.ui.SharedSurveyViewModel
import com.practicum.mateocare.ui.forecast.WeatherForecastState
import com.practicum.mateocare.ui.forecast.WeatherForecastViewModel
import com.practicum.mateocare.ui.forecast.models.WeatherParameterUiModel
import com.practicum.mateocare.ui.profile.AiState
import com.practicum.mateocare.ui.profile.AiViewModel
import com.practicum.mateocare.ui.profile.ProfileState
import com.practicum.mateocare.ui.profile.ProfileViewModel
import com.practicum.mateocare.ui.profile.models.SurveyQuestions
import com.practicum.mateocare.ui.profile.screen.FullSurvey
import org.koin.androidx.compose.koinViewModel
import kotlin.math.abs
import kotlin.math.max

@Composable
fun ShowWeatherForecast(sharedVM: SharedSurveyViewModel ,modifier: Modifier = Modifier) {
    val viewModel: WeatherForecastViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
            viewModel.onPermissionGranted()
        }
    }

    LaunchedEffect(Unit) {
        val isGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (isGranted) {
            viewModel.onPermissionGranted()
        } else {
            launcher.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        }
    }

    val isTodaySurveyDone by sharedVM.isSurveyDoneToday.collectAsState()

    val todayStatus by sharedVM.todayStatus.collectAsState()


    //анимация раскрытия жлемента
    val expandedItemId = remember { mutableStateOf<Int?>(null) }

    Box(
        modifier = modifier
    ) {

        when(state) {
            is WeatherForecastState.Content -> {
                ContentScreen(
                    items = (state as WeatherForecastState.Content).forecasts,
                    expandedItemId = expandedItemId,
                    getItemDetails = { itemId -> viewModel.getDetailsForecast(itemId)},
                    getTimes = {viewModel.getTimes()},
                    fullForecast = (state as WeatherForecastState.Content).fullForecasts,
                    onUpdateStatus = {userId, status, advice ->
                        sharedVM.updateStatus(status)
                        sharedVM.saveDailyStatus(userId, advice)
                    },
                    checkTodayStatus = {userId ->
                        sharedVM.checkTodayStatus(userId)
                        Log.d("daily", isTodaySurveyDone.toString())
                        isTodaySurveyDone
                    },
                    getTodayStatus = {userId ->
                        sharedVM.fetchTodayStatus(userId)
                        todayStatus
                    }
                )
            }
            is WeatherForecastState.Empty -> {
                ErrorMessage((state as WeatherForecastState.Empty).message)
            }
            is WeatherForecastState.Error -> {
                ErrorMessage((state as WeatherForecastState.Error).errorMessage)
            }
            is WeatherForecastState.Loading -> {
                LoadingScreen()
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp)
        )
    }
}

@Composable
fun ErrorMessage(message: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier
        )
    }
}

@Composable
fun ContentScreen(
    items: List<WeatherParameterUiModel>,
    expandedItemId: MutableState<Int?>,
    getItemDetails: (Int) -> List<Any>,
    getTimes: () -> List<String>,
    fullForecast: WeatherForecast,
    onUpdateStatus: (String, String, String) -> Unit,
    checkTodayStatus: (String) -> Boolean,
    getTodayStatus: (String) -> UserDailyStatus?,
    modifier: Modifier = Modifier) {
    val itemPairs = remember(items) { items.chunked(2) }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(
            items = itemPairs,
            key = { pair -> pair.joinToString(separator = "-") { it.id.toString() } }
        ) { pair ->
            val expandedItem = pair.firstOrNull { it.id == expandedItemId.value }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    pair.forEach { item ->
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.TopStart
                        ) {
                            androidx.compose.animation.AnimatedVisibility(
                                visible = expandedItemId.value != item.id,
                                enter = fadeIn() + scaleIn() + expandVertically(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                ),
                                exit = fadeOut() + scaleOut() + shrinkVertically(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioNoBouncy,
                                        stiffness = Spring.StiffnessMediumLow
                                    )
                                )
                            ) {
                                WeatherCard(
                                    item = item,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(0.9f)
                                        .clickable {
                                            expandedItemId.value =
                                                if (expandedItemId.value == item.id) null else item.id
                                        }
                                )
                            }
                        }
                    }

                    if (pair.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                AnimatedVisibility(
                    visible = expandedItem != null,
                    enter = fadeIn() + slideInVertically(
                        initialOffsetY = { it / 3 }
                    ) + expandVertically(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    ),
                    exit = fadeOut() + slideOutVertically(
                        targetOffsetY = { -it / 6 }
                    ) + shrinkVertically(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
                ) {
                    expandedItem?.let { item ->
                        WeatherCard(
                            item = item,
                            detailItems = getItemDetails(item.id),
                            times = getTimes(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    expandedItemId.value = null
                                }
                        )
                    }
                }
            }
        }
        item{
            val profileViewModel: ProfileViewModel = koinViewModel()
            val profileState by profileViewModel.uiState.collectAsState()

            when(profileState) {
                is ProfileState.RegisterUser -> {
                    if ((profileState as ProfileState.RegisterUser).meteoStatus != null) {
                        if(checkTodayStatus((profileState as ProfileState.RegisterUser).userId)) {
                            val userDailyStatus = getTodayStatus((profileState as ProfileState.RegisterUser).userId)

                            Text(
                                text = userDailyStatus?.advice ?: "Не определен",
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .padding(bottom=16.dp)
                                    .fillMaxWidth()
                                    .clip(shape= RoundedCornerShape(24.dp))
                                    .background(color= MaterialTheme.colorScheme.tertiaryContainer)
                                    .padding(16.dp)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Ваше сегодняшнее состояние: "
                                )
                                DailyHealth(userDailyStatus?.status ?: "default")
                            }
                        } else {
                            val aiViewModel: AiViewModel = koinViewModel()
                            val aiState by aiViewModel.uiState.collectAsState()

                            val dailyQuestions = SurveyQuestions.Daily

                            //открытие окна с опросом
                            var showSurvey by remember { mutableStateOf(false) }

                            // Переменная для хранения ответов на случай ошибки
                            var lastAnswers by remember { mutableStateOf("") }

                            when(aiState) {
                                is AiState.Initial -> {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Пройдите ежедневный опрос для улучшения ежедневных советов",
                                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier
                                                .padding(bottom = 16.dp)
                                                .fillMaxWidth()
                                                .clip(shape= RoundedCornerShape(24.dp))
                                                .background(color = MaterialTheme.colorScheme.tertiaryContainer)
                                                .padding(16.dp)
                                        )

                                        Button(
                                            onClick = { showSurvey = true },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = MaterialTheme.colorScheme.tertiary,
                                                contentColor = MaterialTheme.colorScheme.surface,
                                                disabledContentColor = MaterialTheme.colorScheme.errorContainer,
                                                disabledContainerColor = MaterialTheme.colorScheme.error
                                            )
                                        ) {
                                            Text(
                                                text = "Пройти".uppercase(),
                                                style = MaterialTheme.typography.titleLarge
                                            )
                                        }
                                    }

                                    if(showSurvey) {
                                        FullSurvey(
                                            { showSurvey = false },
                                            dailyQuestions,
                                            { answers ->
                                                val finalReport = answers.map { "${it.key}: ${it.value}"}.joinToString("; ")
                                                lastAnswers = finalReport
                                                aiViewModel.getAdvice(
                                                    (profileState as ProfileState.RegisterUser).age,
                                                    (profileState as ProfileState.RegisterUser).meteoStatus!!,
                                                    fullForecast,
                                                    finalReport
                                                )
                                            }
                                        )
                                    }
                                }
                                is AiState.Content -> {
                                    LaunchedEffect((aiState as AiState.Content).meteoStatus.level) {
                                        onUpdateStatus((profileState as ProfileState.RegisterUser).userId, (aiState as AiState.Content).meteoStatus.level, (aiState as AiState.Content).advice)
                                    }

                                    Text(
                                        text = (aiState as AiState.Content).advice,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier
                                            .padding(bottom=16.dp)
                                            .fillMaxWidth()
                                            .clip(shape= RoundedCornerShape(24.dp))
                                            .background(color= MaterialTheme.colorScheme.tertiaryContainer)
                                            .padding(16.dp)
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Ваше сегодняшнее состояние: "
                                        )
                                        DailyHealth((aiState as AiState.Content).meteoStatus.level)
                                    }
                                }
                                is AiState.Error -> ErrorMessage("Не удалось загрузить совет")
                                is AiState.Loading -> TypingDotsAnimation(
                                    modifier = Modifier
                                        .padding(bottom=16.dp)
                                        .fillMaxWidth()
                                        .clip(shape= RoundedCornerShape(24.dp))
                                        .background(color= MaterialTheme.colorScheme.tertiaryContainer)
                                        .padding(16.dp)
                                )
                            }
                        }
                    }
                }
                else -> {}
            }
        }

    }
}

@Composable
fun DailyHealth(stringHealth: String) {
    when(stringHealth){
        "high" -> {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(color = Color.Red, shape = CircleShape)
            )
        }
        "medium" -> {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(color = Color.Blue, shape = CircleShape)
            )
        }
        "low" -> {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(color = Color.Green, shape = CircleShape)
            )
        }
        else -> {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(color = Color.Gray, shape = CircleShape)
            )
        }
    }
}

@Composable
fun TypingDotsAnimation(
    modifier: Modifier = Modifier,
    prefix: String = "Загрузка",
    style: TextStyle = MaterialTheme.typography.bodyLarge
) {
    val infiniteTransition = rememberInfiniteTransition(label = "typing")
    val dotCount by infiniteTransition.animateValue(
        initialValue = 0,
        targetValue = 4,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "dots"
    )

    Text(
        text = prefix + ".".repeat(dotCount),
        style = style,
        modifier = modifier
    )
}

@Composable
fun WeatherCard(item: WeatherParameterUiModel, modifier: Modifier = Modifier, detailItems: List<Any>? = null, times: List<String>? = null){
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        if(detailItems.isNullOrEmpty()){
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = item.value + " " + item.unit,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.surface
            )
        } else {
            Row {
                Text(
                    text = item.name + ":",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Spacer(
                    Modifier.width(4.dp)
                )
                Text(
                    text = item.value,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Spacer(
                    Modifier.width(4.dp)
                )
                Text(
                    text = item.unit,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            val detailScrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .horizontalScroll(detailScrollState)
                    .padding(top = 16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.height(24.dp)
                ) {
                    times?.forEach { time ->
                        Text(
                            text = time,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(56.dp)
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    detailItems.forEach { detailItem ->
                        Text(
                            text = detailItem.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.surface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(56.dp)
                        )
                    }
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width((detailItems.size * 56 + (detailItems.size - 1).coerceAtLeast(0) * 24).dp)
                        .height(128.dp)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    DetailedWeatherChart(data = detailItems)
                }
            }
        }
    }
}

@Composable
fun DetailedWeatherChart(data: List<Any>) {
    val values = data.mapNotNull {
        when (it) {
            is Number -> {
                it.toFloat()
            }
            is String -> it.toFloatOrNull()
            else -> null
        }
    }

    if (values.isEmpty()) return

    val tertiaryColor = MaterialTheme.colorScheme.tertiary
    val chartModelProducer = remember { CartesianChartModelProducer() }
    val minValue = values.minOrNull()?.toDouble() ?: return
    val maxValue = values.maxOrNull()?.toDouble() ?: return
    val valueRange = maxValue - minValue
    val yPadding = if (valueRange == 0.0) {
        max(abs(minValue) * 0.1, 1.0)
    } else {
        valueRange * 0.2
    }
    val rangeProvider = remember(minValue, maxValue) {
        CartesianLayerRangeProvider.fixed(
            minY = minValue - yPadding,
            maxY = maxValue + yPadding
        )
    }

    LaunchedEffect(values) {
        chartModelProducer.runTransaction {
            lineSeries {
                series(values)
            }
        }
    }

    val pointComponent = rememberShapeComponent(
        fill = fill(tertiaryColor),
        shape = CircleShape.toVicoShape()
    )
    val line = LineCartesianLayer.rememberLine(
        fill = LineCartesianLayer.LineFill.single(fill(tertiaryColor)),
        stroke = LineCartesianLayer.LineStroke.continuous(
            thickness = 2.dp,
            cap = StrokeCap.Round
        ),
        pointProvider = LineCartesianLayer.PointProvider.single(
            LineCartesianLayer.point(
                component = pointComponent,
                size = 10.dp
            )
        )
    )
    val chart = rememberCartesianChart(
        rememberLineCartesianLayer(
            lineProvider = LineCartesianLayer.LineProvider.series(line),
            pointSpacing = 70.dp,
            rangeProvider = rangeProvider
        )
    )

    ProvideVicoTheme(
        theme = rememberM3VicoTheme(lineCartesianLayerColors = listOf(tertiaryColor))
    ) {
        CartesianChartHost(
            chart = chart,
            modelProducer = chartModelProducer,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 23.dp, vertical = 12.dp),
            scrollState = rememberVicoScrollState(scrollEnabled = false)
        )
    }
}