package presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import presentation.ui.screens.screen1.screen.Homescreen
import presentation.ui.screens.screen2.screen.CurrentTripScreen
import presentation.ui.screens.screen3.screen.AddExpenseScreen
import presentation.ui.screens.screen4.screen.PastTripsScreen
import presentation.ui.screens.screen5.screen.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Homescreen()
            CurrentTripScreen()
            AddExpenseScreen()
            PastTripsScreen()
            LoginScreen()
        }
    }
}