import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import presentation.navigation.BottomBarNavigationItems
import presentation.navigation.NavGraph
import presentation.navigation.Screen
import presentation.ui.components.BottomBarNavigationComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val backStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry.value?.destination?.route
            val showBottomBar = Screen.bottomBarRoutes().any {
                currentRoute?.startsWith(it.substringBefore("/{")) == true
            }
            val selectedIndex = BottomBarNavigationItems.items.indexOfFirst {
                currentRoute?.startsWith(it.route.substringBefore("/{")) == true
            }.coerceAtLeast(0)

            Scaffold(
                bottomBar = {
                    if (showBottomBar) {
                        BottomBarNavigationComponent(
                            items = BottomBarNavigationItems.items,
                            selectedItemIndex = selectedIndex,
                            onItemSelected = { index ->
                                val route = BottomBarNavigationItems.items[index].route
                                navController.navigate(route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            ) { padding ->
                NavGraph(
                    navController = navController,
                    startDestination = Screen.LoginScreen.route,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}