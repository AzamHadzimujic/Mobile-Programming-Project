package presentation.ui.screens.screen1.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import presentation.ui.R
import presentation.ui.components.SwitchNotification
import androidx.compose.ui.tooling.preview.Preview
import presentation.ui.components.InfoBox

@Composable
fun Homescreen() {

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.vacation1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
            Text(
                "Welcome to Trip Budget Planner",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                item {
                    LazyRow {
                        item {
                            InfoBox("Manage Expenses", Color.Green)
                        }
                        item {
                            InfoBox("View Past Trips", Color.Magenta)
                        }
                    }
                }
                item {
                    LazyRow {
                        item {
                            InfoBox("Track Spending", Color.LightGray)
                        }
                        item {
                            InfoBox("Manage your Account", Color.Cyan)
                        }
                    }
                }
                item {
                    SwitchNotification()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen1() {
    MaterialTheme {
        Homescreen()
    }
}