package presentation.navigation

sealed class Screen(val route: String) {
    // LOGIN
    data object LoginScreen : Screen("login")
    // HOME
    data object HomeScreen : Screen("home")
    // CURRENT TRIP
    data object CurrentTrip : Screen("current_trip/{tripId}") {
        fun createRoute(tripId: String): String {
            return "current_trip/$tripId"
        }
    }
    // ADD EXPENSE
    data object AddExpense : Screen("add_expense/{tripId}") {
        fun createRoute(tripId: String): String {
            return "add_expense/$tripId"
        }
    }

    // PAST TRIPS
    data object PastTrip : Screen("past_trip")
    companion object {
        fun bottomBarRoutes() = listOf(
            HomeScreen.route,
            CurrentTrip.route,
            PastTrip.route
        )
    }
}