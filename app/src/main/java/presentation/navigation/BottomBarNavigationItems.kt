package presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.History

object BottomBarNavigationItems {
    val items = listOf(
        BottomBarNavigationItem(
            title = "Home",
            route = Screen.HomeScreen.route,
            icon = Icons.Default.Home
        ),
        BottomBarNavigationItem(
            title = "Current Trip",
            route = Screen.CurrentTrip.route,
            icon = Icons.Default.List
        ),
        BottomBarNavigationItem(
            title = "Past Trips",
            route = Screen.PastTrip.route,
            icon = Icons.Default.History
        )
    )
}