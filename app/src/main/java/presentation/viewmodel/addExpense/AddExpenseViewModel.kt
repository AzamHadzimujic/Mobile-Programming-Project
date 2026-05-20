package presentation.viewmodel.addExpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import model.data.local.entity.ExpenseEntity
import model.repository.expense.ExpenseRepository
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<AddExpenseUiState>(
            AddExpenseUiState.Init
        )
    val uiState: StateFlow<AddExpenseUiState> =
        _uiState.asStateFlow()
    private val _formState =
        MutableStateFlow(AddExpenseFormState())
    val formState =
        _formState.asStateFlow()
    private val _navigationEvent =
        Channel<AddExpenseNavigationEvent>(
            Channel.BUFFERED
        )
    val navigationEvent =
        _navigationEvent.receiveAsFlow()
    fun onTitleChange(value: String) {
        _formState.update {
            it.copy(title = value)
        }
    }
    fun onAmountChange(value: String) {
        _formState.update {
            it.copy(amount = value)
        }
    }
    fun onCategoryChange(value: String) {
        _formState.update {
            it.copy(category = value)
        }
    }
    fun saveExpense() {
        val current = _formState.value
        if (
            current.title.isBlank() ||
            current.amount.isBlank() ||
            current.category.isBlank()
        ) {
            _uiState.value =
                AddExpenseUiState.Error(
                    "Please fill all fields"
                )
            return
        }
        viewModelScope.launch {
            _uiState.value =
                AddExpenseUiState.Loading
            try {
                repository.insertExpense(
                    ExpenseEntity(
                        title = current.title,
                        amount = current.amount,
                        categories = current.category
                    )
                )
                _uiState.value =
                    AddExpenseUiState.Success
                _navigationEvent.send(
                    AddExpenseNavigationEvent.NavigateBack
                )
            } catch (e: Exception) {
                _uiState.value =
                    AddExpenseUiState.Error(
                        e.message ?: "Failed to save expense"
                    )
            }
        }
    }
    fun resetUiState() {
        _uiState.value = AddExpenseUiState.Init
    }
}