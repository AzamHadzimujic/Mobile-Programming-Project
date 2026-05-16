package presentation.viewmodel.addExpense

sealed interface AddExpenseUiState {
    data object Init : AddExpenseUiState
    data object Loading : AddExpenseUiState
    data object Success : AddExpenseUiState
    data class Error(
        val message: String
    ) : AddExpenseUiState
}