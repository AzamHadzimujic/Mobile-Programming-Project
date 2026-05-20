package presentation.viewmodel.currentTrip
import model.data.local.entity.ExpenseEntity

sealed interface CurrentTripUiState {
    data object Init : CurrentTripUiState
    data object Loading : CurrentTripUiState
    data class Success(
        val searchQuery: String,
        val expenses: List<ExpenseEntity>
    ) : CurrentTripUiState
    data class Error(
        val message: String
    ) : CurrentTripUiState
}