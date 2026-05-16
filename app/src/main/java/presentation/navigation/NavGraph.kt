package presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import presentation.ui.screens.AddExpenseScreen
import presentation.ui.screens.LoginScreen
import presentation.ui.screens.PastTripScreen
import presentation.ui.screens.CurrentTripScreen
import presentation.ui.screens.Homescreen
import presentation.viewmodel.auth.LoginViewModel

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
        // LOGIN
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                viewModel = hiltViewModel<LoginViewModel>(),
                onNavigate = {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.LoginScreen.route) { inclusive = true }
                    }
                }
            )
        }
        // HOMESCREEN
        composable(Screen.HomeScreen.route) {
            Homescreen(
                viewModel = hiltViewModel(),
                onScreenClick = { route ->
                    navController.navigate(route)
                }
            )
        }
        // ADD EXPENSE
        composable(
            route = Screen.AddExpense.route,
            arguments = listOf(
                navArgument("tripId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("tripId")
            AddExpenseScreen(
                viewModel = hiltViewModel(),
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        // CURRENT TRIP
        composable(
            route = Screen.CurrentTrip.route,
            arguments = listOf(
                navArgument("tripId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId")
            CurrentTripScreen(
                tripId = tripId ?: "",
                viewModel = hiltViewModel(),
                onAddExpenseClick = {
                    navController.navigate(
                        Screen.AddExpense.createRoute(tripId ?: "")
                    )
                }
            )
        }
        // PAST TRIPS
        composable(Screen.PastTrip.route) {
            PastTripScreen(
                viewModel = hiltViewModel(),
                onTripClick = { tripId ->
                    navController.navigate(
                        Screen.CurrentTrip.createRoute(tripId)
                    )
                }
            )
        }
    }
}