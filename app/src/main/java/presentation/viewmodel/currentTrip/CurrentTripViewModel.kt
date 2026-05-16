package presentation.viewmodel.currentTrip

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.ExpenseItem
import javax.inject.Inject

@HiltViewModel
class CurrentTripViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<CurrentTripUiState>(
        CurrentTripUiState.Loading
    )
    val uiState: StateFlow<CurrentTripUiState> = _uiState.asStateFlow()

    private val expenses = MutableStateFlow<List<ExpenseItem>>(emptyList())

    private var searchQuery = ""

    init {
        val tripId = ""
        loadInitialExpenses(tripId)
    }

    // ─────────────────────────────
    // INIT DATA
    // ─────────────────────────────
    fun loadInitialExpenses(tripId: String) {
        expenses.value = listOf(
            ExpenseItem("1", "DoorDash delivery", "Food", "25"),
            ExpenseItem("2", "Museum Visit", "Culture", "30"),
            ExpenseItem("3", "Lunch at the beach", "Food", "150"),
            ExpenseItem("4", "Public Transport", "Transport", "10"),
        )

        publishState()
    }

    // ─────────────────────────────
    // ADD EXPENSE
    // ─────────────────────────────
    fun addExpense(title: String) {
        val newItem = ExpenseItem(
            id = generateId(),
            title = title,
            categories = "New",
            amount = "0"
        )

        expenses.update { it + newItem }
        publishState()
    }

    // ─────────────────────────────
    // DELETE EXPENSE
    // ─────────────────────────────
    fun deleteExpense(id: String) {
        expenses.update { list ->
            list.filterNot { it.id == id }
        }
        publishState()
    }

    // ─────────────────────────────
    // SEARCH
    // ─────────────────────────────
    fun updateSearch(query: String) {
        searchQuery = query
        publishState()
    }

    // ─────────────────────────────
    // STATE BUILDER
    // ─────────────────────────────
    private fun publishState() {
        val filtered = expenses.value.filter { expense ->
            expense.title.contains(searchQuery, ignoreCase = true)
        }

        _uiState.value = CurrentTripUiState.Success(
            searchQuery = searchQuery,
            expenses = filtered
        )
    }

    // ─────────────────────────────
    // RESET
    // ─────────────────────────────
    fun resetUiState() {
        searchQuery = ""
        publishState()
    }

    private fun generateId(): String {
        return (expenses.value.size + 1).toString()
    }
}