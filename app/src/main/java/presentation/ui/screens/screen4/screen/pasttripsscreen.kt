package presentation.ui.screens.screen4.screen

import androidx.compose.foundation.layout.Column
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

@Composable
fun PastTripsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Your past Trips are here!",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            item {
                TripItem("Italy Trip", "500€", Color.Magenta)
            }
            item {
                TripItem("Spain Trip", "300€", Color.Cyan)
            }
            item {
                TripItem("Germany Trip", "150€", Color.LightGray)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen4() {
    MaterialTheme {
        PastTripsScreen()
    }
}