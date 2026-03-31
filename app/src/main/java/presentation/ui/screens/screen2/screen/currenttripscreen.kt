package presentation.ui.screens.screen2.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import presentation.ui.screens.screen1.screen.InfoBox

@Composable
fun CurrentTripScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Text(
            "Current Trip Expenses",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            item {
                Row {
                    InfoBox("Paris, the City of Love", Color.Yellow)
                    InfoBox("Total: 200€", Color.LightGray)
                }
            }
            items(2) {
                ExpenseItem("Food", "20€")
            }
            items(3) {
                ExpenseItem("Museum", "10€")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen2() {
    MaterialTheme {
        CurrentTripScreen()
    }
}