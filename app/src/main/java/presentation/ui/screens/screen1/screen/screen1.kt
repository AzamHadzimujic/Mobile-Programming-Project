package presentation.ui.screens.screen1.screen

import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
import presentation.ui.sharedComponents.CheckboxExample

@Composable
fun Screen1(){
    CheckboxExample()
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen1() {
    MaterialTheme {
        CheckboxExample()
    }
}