package presentation.viewmodel.currentTrip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import model.data.local.entity.ExpenseEntity
import model.repository.expense.ExpenseRepository
import javax.inject.Inject

@HiltViewModel
class CurrentTripViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<CurrentTripUiState>(
            CurrentTripUiState.Loading
        )
    val uiState: StateFlow<CurrentTripUiState> =
        _uiState.asStateFlow()
    private var searchQuery = ""
    init {
        observeExpenses()
    }
    private fun observeExpenses() {
        viewModelScope.launch {
            repository.getAllExpenses()
                .collect { expenses ->
                    publishState(expenses)
                }
        }
    }
    fun deleteExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }
    fun updateSearch(query: String) {
        searchQuery = query
        viewModelScope.launch {
            repository.getAllExpenses()
                .first()
                .let { publishState(it) }
        }
    }
    private fun publishState(
        expenses: List<ExpenseEntity>
    ) {
        val filtered =
            expenses.filter { expense ->

                expense.title.contains(
                    searchQuery,
                    ignoreCase = true
                )
            }
        _uiState.value =
            CurrentTripUiState.Success(
                searchQuery = searchQuery,
                expenses = filtered
            )
    }
    fun resetUiState() {
        searchQuery = ""
    }
}