package presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import presentation.ui.screens.AddExpenseScreen
import presentation.ui.screens.CurrentTripScreen
import presentation.ui.screens.Homescreen
import presentation.ui.screens.LoginScreen
import presentation.ui.screens.PastTripScreen
import presentation.viewmodel.login.LoginViewModel

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
        composable(
            Screen.LoginScreen.route
        ) {
            LoginScreen(
                viewModel =
                    hiltViewModel<LoginViewModel>(),
                onNavigate = {
                    navController.navigate(
                        Screen.HomeScreen.route
                    ) {
                        popUpTo(
                            Screen.LoginScreen.route
                        ) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        // HOME
        composable(
            Screen.HomeScreen.route
        ) {
            Homescreen(
                viewModel = hiltViewModel(),
                onScreenClick = { route ->
                    navController.navigate(route)
                }
            )
        }
        // ADD EXPENSE
        composable(
            Screen.AddExpense.route
        ) {
            AddExpenseScreen(
                viewModel = hiltViewModel(),
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        // CURRENT TRIP
        composable(
            Screen.CurrentTrip.route
        ) {
            CurrentTripScreen(
                viewModel = hiltViewModel(),
                onAddExpenseClick = {
                    navController.navigate(
                        Screen.AddExpense.route
                    )
                }
            )
        }
        // PAST TRIPS
        composable(
            Screen.PastTrip.route
        ) {
            PastTripScreen(
                viewModel = hiltViewModel(),
                onTripClick = {
                    navController.navigate(
                        Screen.CurrentTrip.route
                    )
                }
            )
        }
    }
}