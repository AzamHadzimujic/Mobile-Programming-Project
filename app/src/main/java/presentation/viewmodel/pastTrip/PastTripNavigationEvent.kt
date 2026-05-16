package presentation.viewmodel.pastTrip
sealed interface PastTripNavigationEvent {
    data object Navigate: PastTripNavigationEvent
    data object NavigateBack: PastTripNavigationEvent
}