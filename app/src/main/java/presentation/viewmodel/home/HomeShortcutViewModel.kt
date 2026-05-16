package presentation.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import model.ScreenShortcutData
import presentation.navigation.Screen

@HiltViewModel
class HomeShortcutViewModel @Inject constructor() :
    ViewModel() {
    private val _uiState =
        MutableStateFlow<HomeShortcutUiState>(
            HomeShortcutUiState.Init
        )
    val uiState =
        _uiState.asStateFlow()
    private val _navigationEvent =
        Channel<HomeShortcutNavigationEvent>(
            Channel.BUFFERED
        )
    val navigationEvent =
        _navigationEvent.receiveAsFlow()
    private val allShortcuts = listOf(
        ScreenShortcutData(
            "Current Trip",
            Screen.CurrentTrip.route
        ),
        ScreenShortcutData(
            "Add Expense",
            Screen.AddExpense.route
        ),
        ScreenShortcutData(
            "Past Trips",
            Screen.PastTrip.route
        ),
        ScreenShortcutData(
            "Login",
            Screen.LoginScreen.route
        )
    )
    init {
        loadShortcuts()
    }
    fun loadShortcuts() {
        viewModelScope.launch {
            _uiState.value =
                HomeShortcutUiState.Loading
            _uiState.value =
                HomeShortcutUiState.Success(
                    searchQuery = "",
                    shortcuts = allShortcuts
                )
        }
    }
    fun onSearchQueryChange(
        query: String
    ) {
        val filteredShortcuts =
            allShortcuts.filter {
                it.title.contains(
                    query,
                    ignoreCase = true
                )
            }
        _uiState.value =
            HomeShortcutUiState.Success(
                searchQuery = query,
                shortcuts = filteredShortcuts
            )
    }
    fun resetUiState() {
        loadShortcuts()
    }
}