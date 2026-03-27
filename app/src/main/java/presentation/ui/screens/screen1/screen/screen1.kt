package presentation.ui.screens.screen1.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import presentation.ui.sharedComponents.RadioButton
import presentation.ui.sharedComponents.SwitchDarkMode
import presentation.ui.sharedComponents.TermsCheckbox

@Composable
fun Screen1(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TermsCheckbox()
        SwitchDarkMode()
        RadioButton()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen1() {
    MaterialTheme {
        Screen1()
    }
}