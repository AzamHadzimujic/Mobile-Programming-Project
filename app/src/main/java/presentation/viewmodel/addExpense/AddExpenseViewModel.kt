package presentation.viewmodel.addExpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import model.repository.FakeAddExpenseRepository
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class AddExpenseViewModel @Inject constructor() : ViewModel() {
    private val repository = FakeAddExpenseRepository()
    private val _uiState =
        MutableStateFlow<AddExpenseUiState>(
            AddExpenseUiState.Init
        )
    val uiState: StateFlow<AddExpenseUiState> =
        _uiState.asStateFlow()
    private val _navigationEvent =
        Channel<AddExpenseNavigationEvent>(
            Channel.BUFFERED
        )
    val navigationEvent =
        _navigationEvent.receiveAsFlow()
    init {
        loadExpense()
    }
    private fun loadExpense() {
        viewModelScope.launch {
            _uiState.value = AddExpenseUiState.Loading
            try {
                val expenseData = repository.getExpense()
                _uiState.value = AddExpenseUiState.Success(expenseData)
            } catch (e: CancellationException) {
                throw e
            } catch (e: IllegalStateException) {
                _uiState.value = AddExpenseUiState.Error(
                    e.message ?: "Failed to load expense."
                )
            }
        }
    }
    fun resetUiState() {
        loadExpense()
    }
}