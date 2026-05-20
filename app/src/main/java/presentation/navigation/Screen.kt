package presentation.navigation

sealed class Screen(
    val route: String
) {
    // LOGIN
    data object LoginScreen :
        Screen("login")
    // HOME
    data object HomeScreen :
        Screen("home")
    // CURRENT TRIP
    data object CurrentTrip :
        Screen("current_trip")
    // ADD EXPENSE
    data object AddExpense :
        Screen("add_expense")
    // PAST TRIPS
    data object PastTrip :
        Screen("past_trip")
    companion object {
        fun bottomBarRoutes() = listOf(
            HomeScreen.route,
            CurrentTrip.route,
            PastTrip.route
        )
    }
}