package presentation.viewmodel.currentTrip
sealed interface CurrentTripNavigationEvent {
    data object NavigateBack : CurrentTripNavigationEvent
    data class Navigate(val route: String) : CurrentTripNavigationEvent
}