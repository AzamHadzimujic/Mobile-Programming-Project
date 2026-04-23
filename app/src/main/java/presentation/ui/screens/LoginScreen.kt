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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import presentation.ui.components.ButtonCancel
import presentation.ui.components.ButtonSave
import presentation.ui.components.InputField
import presentation.ui.components.RadioButton
import presentation.ui.components.SwitchDarkMode
import presentation.ui.components.SwitchNotification

@Composable
fun LoginScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Text(
            "On this screen you can log in into your account!",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            item {
                InputField("Username", username) { username = it }
                InputField("Password", password) { password = it }
                Spacer(modifier = Modifier.height(16.dp))
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
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ButtonSave(
                        onClick = {
                            if (username.isEmpty() || password.length < 8) {
                                println("Invalid input")}
                        },
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

@Preview(showBackground = true)
@Composable
fun PreviewScreen5() {
    MaterialTheme {
        LoginScreen()
    }
}