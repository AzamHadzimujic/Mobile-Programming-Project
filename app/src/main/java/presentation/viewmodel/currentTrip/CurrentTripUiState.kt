package presentation.viewmodel.currentTrip
import model.ExpenseItem

sealed interface CurrentTripUiState {
    data object Init : CurrentTripUiState
    data object Loading : CurrentTripUiState
    data class Success(
        val searchQuery: String,
        val expenses: List<ExpenseItem>
    ) : CurrentTripUiState
    data class Error(val message: String) : CurrentTripUiState
}