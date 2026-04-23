package presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import presentation.ui.screens.AddExpenseScreen
import presentation.ui.screens.LoginScreen
import presentation.ui.screens.PastTripScreen
import presentation.ui.screens.CurrentTripScreen
import presentation.ui.screens.Homescreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                onNavigateToHomeScreen = {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(Screen.HomeScreen.route) {
            Homescreen(
                onNavigateToCurrentTripScreen = {
                    navController.navigate(Screen.CurrentTrip.createRoute("1"))
                },
                onNavigateToPastTripScreen = {
                    navController.navigate(Screen.PastTrip.route)
                },
                onNavigateToLoginScreen = {
                    navController.navigate(Screen.LoginScreen.route)
                }
            )
        }
        composable(
            route = Screen.AddExpense.route,
            arguments = listOf(
                navArgument("tripId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId")
            AddExpenseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Screen.CurrentTrip.route,
            arguments = listOf(
                navArgument("tripId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId")
            CurrentTripScreen(tripId = tripId ?: "0")
        }
        composable(Screen.PastTrip.route) {
            PastTripScreen(
                onTripClick = { tripId ->
                    navController.navigate(Screen.CurrentTrip.createRoute(tripId.toString()))
                }
            )
        }
    }
}
