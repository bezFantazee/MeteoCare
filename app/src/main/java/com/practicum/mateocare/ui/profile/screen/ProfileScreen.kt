package com.practicum.mateocare.ui.profile.screen

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.practicum.mateocare.profile.domain.models.MeteoStatus
import com.practicum.mateocare.profile.domain.models.UserDailyStatus
import com.practicum.mateocare.ui.SharedSurveyViewModel
import com.practicum.mateocare.ui.forecast.screen.DailyHealth
import com.practicum.mateocare.ui.forecast.screen.ErrorMessage
import com.practicum.mateocare.ui.forecast.screen.LoadingScreen
import com.practicum.mateocare.ui.profile.AiState
import com.practicum.mateocare.ui.profile.AiViewModel
import com.practicum.mateocare.ui.profile.NotificationViewModel
import com.practicum.mateocare.ui.profile.ProfileState
import com.practicum.mateocare.ui.profile.ProfileViewModel
import com.practicum.mateocare.ui.profile.models.Question
import com.practicum.mateocare.ui.profile.models.SurveyQuestions
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProfileScreen(sharedVM: SharedSurveyViewModel, modifier: Modifier = Modifier) {
    val profileViewModel: ProfileViewModel = koinViewModel()
    val profileState by profileViewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()

    //недельный статус пользователя
    val weekUserStatus by sharedVM.weeklyStatuses.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        when(profileState) {
            is ProfileState.UnRegisterUser -> {
                UnRegisterUserScreen(
                    subState = profileState as ProfileState.UnRegisterUser,
                    onShowLogin = { profileViewModel.showLogin() },
                    onShowRegister = { profileViewModel.showRegister() },
                    onRegister = {email, password, age, gender -> profileViewModel.registerUser(email, password, age, gender) },
                    onLogin = {email, password -> profileViewModel.loginUser(email, password)},
                    onBack = { profileViewModel.back() }
                )
            }
            is ProfileState.RegisterUser -> {
                RegisterUserScreen(
                    (profileState as ProfileState.RegisterUser).userId,
                    (profileState as ProfileState.RegisterUser).email,
                    (profileState as ProfileState.RegisterUser).age,
                    (profileState as ProfileState.RegisterUser).gender,
                    (profileState as ProfileState.RegisterUser).meteoStatus,
                    (profileState as ProfileState.RegisterUser).mainAdvice,
                    onGetMeteoStatus = {meteoStatus, advice -> profileViewModel.onGetMeteoStatus(meteoStatus, advice) },
                    getWeekUserStatus = {userId ->
                        sharedVM.fetchWeeklyStatuses(userId)
                        weekUserStatus
                    },
                    logout = {profileViewModel.logout()}
                )
            }
            is ProfileState.Error -> {
                ErrorMessage((profileState as ProfileState.Error).message)
            }
            is ProfileState.Loading -> {
                LoadingScreen()
            }
        }
    }
}

@Composable
fun UnRegisterUserScreen(
    subState: ProfileState.UnRegisterUser,
    onShowLogin: () -> Unit,
    onShowRegister: () -> Unit,
    onRegister: (
        email: String,
        password: String,
        age: Int,
        gender: String
            ) -> Unit,
    onLogin: (
        email: String,
        password: String
            ) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    //состояния пользовательского ввода
    var emailTextState by remember { mutableStateOf("") }
    var passwordTextState by remember { mutableStateOf("") }
    var ageTextState by remember { mutableStateOf("") }

    val options = listOf("Мужской", "Женский")
    var selectedOption by remember { mutableStateOf(options[0]) }

    //основной экран выбора аутентификации

        when(subState) {
            is ProfileState.UnRegisterUser.Initial -> {
                Text(
                    text = "Вы еще не авторизовались",
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.headlineMedium
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = onShowLogin
                    ) {
                        Text(
                            text = "Войти".uppercase()
                        )
                    }

                    Button(
                        onClick = onShowRegister
                    ) {
                        Text(
                            text = "Зарегистрироваться".uppercase()
                        )
                    }
                }
            }
            is ProfileState.UnRegisterUser.LoginMode -> {
                //ВХОД
                    OutlinedTextField(
                        value = emailTextState,
                        onValueChange = { emailTextState = it },
                        label = { Text("Введите email...") },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.primary,
                            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
                        ),
                        modifier = Modifier.padding(16.dp)
                    )

                    OutlinedTextField(
                        value = passwordTextState,
                        onValueChange = { passwordTextState = it },
                        label = { Text("Введите пароль...") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.primary,
                            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
                        ),
                        modifier = Modifier.padding(16.dp)
                    )

                    Button(
                        onClick = { onLogin(emailTextState, passwordTextState) }
                    ) {
                        Text(
                            text = "Войти".uppercase()
                        )
                    }

                    Button(
                        onClick = onBack
                    ) {
                        Text(
                            text = "Назад".uppercase()
                        )
                    }
            }
            is ProfileState.UnRegisterUser.RegisterMode -> {
               //РЕГИСТРАЦИЯ
                    OutlinedTextField(
                        value = emailTextState,
                        onValueChange = { emailTextState = it },
                        label = { Text("Введите email...") },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.primary,
                            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
                        ),
                        modifier = Modifier.padding(16.dp)
                    )

                    OutlinedTextField(
                        value = passwordTextState,
                        onValueChange = { passwordTextState = it },
                        label = { Text("Введите пароль...") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.primary,
                            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
                        ),
                        modifier = Modifier.padding(16.dp)
                    )

                    OutlinedTextField(
                        value = ageTextState,
                        onValueChange = {newValue ->
                            if(newValue.all {it.isDigit()} && newValue.length <= 3) {
                                ageTextState = newValue
                            }
                        },
                        label = { Text("Возраст")},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.primary,
                            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
                        ),
                        modifier = Modifier.padding(16.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        options.forEach { text ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Text(
                                    text = text
                                )
                                RadioButton(
                                    selected = (text == selectedOption),
                                    onClick = { selectedOption = text }
                                )
                            }
                        }
                    }
                    Button(
                        onClick = { onRegister(emailTextState, passwordTextState, ageTextState.toIntOrNull() ?: 0, selectedOption) }
                    ) {
                        Text(
                            text = "Зарегистрироваться".uppercase()
                        )
                    }

                Button(
                    onClick = onBack
                ) {
                    Text(
                        text = "Назад".uppercase()
                    )
                }
            }
        }
    }
@Composable
fun RegisterUserScreen(
    userId: String,
    email: String,
    age: Int,
    gender: String,
    meteoStatus: MeteoStatus?,
    mainAdvice: String?,
    onGetMeteoStatus: (MeteoStatus, String) -> Unit,
    getWeekUserStatus: (String) -> List<UserDailyStatus>,
    logout: () -> Unit
) {
    //открытие окна с опросом
    var showSurvey by remember { mutableStateOf(false) }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = {}
        )

        LaunchedEffect(Unit) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    val notViewModel: NotificationViewModel = koinViewModel()
    notViewModel.enable(12, 0)

    Text(
        text ="Профиль".uppercase(),
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.tertiary
    )

    Text(
        text = "Email: " + email,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
    )

    Text(
        text = "Возраст: " + age.toString(),
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
    )

    Text(
        text = "Пол: " + gender,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
    )

    if(meteoStatus == null || mainAdvice == null) {
        //viewModel
        val aiViewModel: AiViewModel = koinViewModel()
        val aiState by aiViewModel.uiState.collectAsState()

        // Переменная для хранения ответов на случай ошибки
        var lastAnswers by remember { mutableStateOf("") }

        when(aiState) {
            is AiState.Initial -> {
                Text(
                    text = "Ваш статус еще не определен",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text="Пройдите опрос для определения вашего статуса",
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(24.dp))
                        .background(color = MaterialTheme.colorScheme.tertiaryContainer)
                        .padding(16.dp)
                )

                Button(
                    onClick = { showSurvey = true},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.surface,
                        disabledContentColor = MaterialTheme.colorScheme.onErrorContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = "Пройти".uppercase(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Button (
                    onClick = logout
                ) {
                    Text(
                        text = "Выйти".uppercase()
                    )
                }

                if(showSurvey) {
                    FullSurvey(
                        { showSurvey = false },
                        SurveyQuestions.Primary,
                        { allAnswers ->
                            val finalReport = allAnswers.map { "${it.key}: ${it.value}"}.joinToString("; ")
                            Log.d("Ans12", finalReport)
                            lastAnswers = finalReport
                            aiViewModel.getStatus(
                                age,
                                finalReport
                            )
                        }
                    )
                }
            }
            is AiState.Content -> {
                val meteoStatus = (aiState as AiState.Content).meteoStatus
                val advice = (aiState as AiState.Content).advice

                onGetMeteoStatus(meteoStatus, advice)

                Text(
                    text = "Метео статус: " + meteoStatus.type,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .clip(shape=RoundedCornerShape(24.dp))
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                        .padding(16.dp)
                )
                Text(
                    text = advice,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .clip(shape=RoundedCornerShape(32.dp))
                        .background(color = MaterialTheme.colorScheme.tertiaryContainer)
                        .padding(24.dp)
                )
            }
            is AiState.Loading -> {
                LoadingScreen()
            }
            is AiState.Error -> {
                ErrorMessage((aiState as AiState.Error).message)
                Button(
                    onClick = {
                        if(lastAnswers.isNotEmpty()) {
                            aiViewModel.getStatus(age, lastAnswers)
                        }
                    }
                ) {
                    Text(
                        text = "Повторить"
                    )
                }
            }
        }
    } else {
        Text(
            text = "Метео статус: " + meteoStatus.type,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .clip(shape=RoundedCornerShape(24.dp))
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .padding(16.dp)
        )
        Text(
            text = mainAdvice,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .clip(shape=RoundedCornerShape(32.dp))
                .background(color = MaterialTheme.colorScheme.tertiaryContainer)
                .padding(16.dp)
        )

        val weekStatus = getWeekUserStatus(userId)

        if( weekStatus.isNotEmpty()) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("История самочувствия: " )
                weekStatus.forEach { status ->
                    DailyHealth(status.status)
                }
            }
        }

        Button (
            onClick = logout
        ) {
            Text(
                text = "Выйти".uppercase()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullSurvey(onDismissRequest: () -> Unit, questions: List<Question>, onFinishButtomClicked: (Map<String, String>) -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val scrollState = rememberScrollState()

    val allAnswers = remember { mutableStateMapOf<String, String>() }

    LaunchedEffect(questions) {
        questions.forEach { question ->
            if (!allAnswers.containsKey(question.text)) {
                allAnswers[question.text] = question.answers[0]
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Text(
                text = "Пройдите опрос на метеозависимость",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            questions.forEach { question ->
                SurveyElement(
                    title = question.text,
                    options = question.answers,
                    onSelectedChanged = { ans ->
                        Log.d("Ans12", ans)
                        allAnswers[question.text] = ans
                    }
                )
            }

            Button(
                onClick = { onFinishButtomClicked(allAnswers) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.surface,
                    disabledContentColor = MaterialTheme.colorScheme.onErrorContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = "Завершить".uppercase(),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
fun SurveyElement(title: String, options: List<String>, onSelectedChanged: (String) -> Unit){
    var selectedOption by remember { mutableStateOf(options[0]) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        options.forEach { text ->
            Row (
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            selectedOption = text
                            onSelectedChanged(text)
                        }
                    )
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.tertiaryContainer,
                        unselectedColor = MaterialTheme.colorScheme.outline
                    )
                )

                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

}