package presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import presentation.ui.components.ButtonCancel
import presentation.ui.components.ButtonSave
import presentation.ui.components.ErrorScreen
import presentation.ui.components.InputField
import presentation.ui.components.LoadingScreen
import presentation.ui.components.RadioButton
import presentation.ui.components.SwitchDarkMode
import presentation.ui.components.SwitchNotification
import presentation.viewmodel.auth.LoginNavigationEvent
import presentation.viewmodel.auth.LoginUiState

@Composable
fun LoginScreen(
    viewModel: presentation.viewmodel.auth.LoginViewModel,
    onNavigate: () -> Unit
) {
    val uiState by viewModel
        .uiState
        .collectAsStateWithLifecycle()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                LoginNavigationEvent.Navigate -> {
                    onNavigate()
                }
                LoginNavigationEvent.NavigateBack -> {
                    // do nothing
                }
            }
        }
    }
    when (uiState) {
        LoginUiState.Loading -> {
            LoadingScreen()
        }
        is LoginUiState.Error -> {
            ErrorScreen(
                message = (uiState as LoginUiState.Error).message
            )
        }
        else -> {
            LoginScreenContent(
                username = username,
                password = password,
                onUsernameChange = {},
                onPasswordChange = {},
                onLoginClick = {
                    if (
                        username.isEmpty() ||
                        password.length < 8
                    ) {
                        println("Invalid input")
                    }
                }
            )
        }
    }
}
@Composable
fun LoginScreenContent(
    username: String,
    password: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "On this screen you can log in into your account!",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                InputField(
                    "Username",
                    username,
                    onUsernameChange
                )
                InputField(
                    "Password",
                    password,
                    onPasswordChange
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }
            item {
                RadioButton()
            }
            item {
                SwitchNotification()
            }
            item {
                SwitchDarkMode()
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {
                    ButtonSave(
                        onClick = onLoginClick,
                        modifier = Modifier.weight(1f)
                    )
                    ButtonCancel(
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}