package presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import presentation.ui.screens.screen1.screen.Screen1
import presentation.ui.screens.screen2.screen.Screen2
import presentation.ui.screens.screen3.screen.Screen3
import presentation.ui.screens.screen4.screen.Screen4
import presentation.ui.screens.screen5.screen.Screen5

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Screen1()
            Screen2()
            Screen3()
            Screen4()
            Screen5()
        }
    }
}