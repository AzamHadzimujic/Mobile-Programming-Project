package presentation.ui.screens.screen3.screen

import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
import presentation.ui.sharedComponents.TermsCheckbox

@Composable
fun Screen3(){
    TermsCheckbox()
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen3() {
    MaterialTheme {
        TermsCheckbox()
    }
}