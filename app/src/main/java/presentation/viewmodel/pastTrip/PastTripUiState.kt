package presentation.viewmodel.pastTrip

import model.data.local.entity.PastTripEntity

sealed interface PastTripUiState {
    data object Loading : PastTripUiState
    data class Success(
        val searchQuery: String,
        val pastTrips: List<PastTripEntity>
    ) : PastTripUiState
    data class Error(
        val message: String
    ) : PastTripUiState
}