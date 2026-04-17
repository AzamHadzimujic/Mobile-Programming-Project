package presentation.navigation

sealed class Screen(val route: String){
    data object AddExpense: Screen("AddExpenseScreen/{tripId}") {
        fun createRoute(tripId: String) = "AddExpenseScreen/$tripId"
    }
    data object CurrentTrip: Screen("CurrentTripScreen/{tripId}") {
        fun createRoute(tripId: String) = "CurrentTripScreen/$tripId"
    }
    data object HomeScreen: Screen("HomeScreen")
    data object LoginScreen: Screen("LoginScreen")
    data object PastTrip: Screen("PastTripScreen")
}