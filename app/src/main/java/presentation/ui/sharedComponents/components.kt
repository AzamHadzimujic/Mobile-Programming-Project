package presentation.ui.sharedComponents

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.ui.semantics.Role
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.text.input.PasswordVisualTransformation

// --- BUTTON FOR SAVE ---
@Composable
    fun ButtonSave() {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                Toast.makeText(context, "Changes have been saved", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("Save")
        }
    }
}
// --- BUTTON FOR CANCEL ---
@Composable
    fun ButtonCancel() {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedButton(
            onClick = {
                Toast.makeText(context, "Action cancelled!", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("Cancel")
        }
    }
}
// --- BUTTON FOR LEARN MORE ---
@Composable
    fun ButtonLearnMore() {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextButton(
            onClick = {
                Toast.makeText(context, "Thank you!", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("Learn More")
        }
    }
}
// --- CHECKBOX FOR TOS ---
@Composable
    fun Checkbox() {
    val context = LocalContext.current
    var isChecked by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable{
                    isChecked = !isChecked
                }
                .padding(vertical = 8.dp)
        ){
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                        Toast
                            .makeText(
                                context,
                                if (isChecked) "You accepted the ToS" else "You declined the ToS",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                }
            )
        }
    }
}
// --- RADIO BUTTON FOR GENDER ---
@Composable
    fun RadioButton() {
    val context = LocalContext.current
    val options = listOf("Female", "Male", "Other")
    var selectedOption by remember { mutableStateOf(options[0]) }
    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
    ) {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (option == selectedOption),
                        onClick = {
                            selectedOption = option
                            Toast
                                .makeText(
                                    context,
                                    "You have selected $option",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        },
                        role = Role.RadioButton
                    )
                    .padding(vertical = 6.dp)
            ) {
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = null
                )
                Text(
                    text = option,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
// --- SWITCH FOR DARK MODE ---
@Composable
    fun SwitchDarkMode() {
    val context = LocalContext.current
    var darkModeEnabled by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dark Mode")
            Switch(
                checked = darkModeEnabled,
                onCheckedChange = {
                    darkModeEnabled = it
                    Toast
                        .makeText(
                            context,
                            if (it) "Dark mode enabled!" else "Dark mode disabled!",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                },
                thumbContent = {
                    Icon(
                        imageVector = if (darkModeEnabled)
                            Icons.Filled.Check
                        else
                            Icons.Filled.Close,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize)
                    )
                }
            )
        }
    }
}
// --- SWITCH FOR PUSH NOTIFICATIONS ---
@Composable
fun SwitchNotification() {
    val context = LocalContext.current
    var notificationsEnabled by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
            "Receive alerts for messages",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        }
        Switch(
            checked = notificationsEnabled,
            onCheckedChange = {
                notificationsEnabled = it
                Toast
                    .makeText(
                        context,
                        if (it) "Notifications enabled!" else "Notifications disabled!",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        )
    }
}
// --- SIMPLE FORM (TRANSFER TO PAGE THAT USES THIS SINCE IT IS NOT SHARED!!!) ---
@Composable
fun SubmitForm() {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isFormValid = username.isNotBlank() && password.length >= 8
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password (min 8 chars)") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                Toast.makeText(
                    context,
                    "Form submitted for $username",
                    Toast.LENGTH_SHORT
                ).show()
            },
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
        if (!isFormValid) {
            Text(
                text = "Username required and password must be at least 8 characters.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

// --- PREVIEW ---
@Preview(showBackground = true)
@Composable
fun PreviewButtonVariations() {
    MaterialTheme {
        ButtonSave()
        ButtonCancel()
        ButtonLearnMore()
        Checkbox()
        RadioButton()
        SwitchDarkMode()
        SwitchNotification()
        SubmitForm()
    }
}