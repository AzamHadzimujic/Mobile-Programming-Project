package presentation.viewmodel.pastTrip

sealed interface PastTripUiState {
    data object Init : PastTripUiState
    data object Loading : PastTripUiState
    data object Success : PastTripUiState
    data class Error(val message: String) : PastTripUiState
}