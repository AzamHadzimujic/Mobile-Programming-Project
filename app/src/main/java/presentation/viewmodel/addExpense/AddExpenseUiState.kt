package presentation.viewmodel.addExpense

import model.ExpenseItem

sealed interface AddExpenseUiState {
    data object Init : AddExpenseUiState
    data object Loading : AddExpenseUiState
    data class Success(val expenseItem: ExpenseItem) : AddExpenseUiState
    data class Error(val message: String) : AddExpenseUiState
}