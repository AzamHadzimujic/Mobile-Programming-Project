package presentation.viewmodel.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import model.ScreenShortcutData
import presentation.navigation.Screen
import javax.inject.Inject

@HiltViewModel
class HomeShortcutViewModel @Inject constructor() :
    ViewModel() {
    private val allShortcuts = listOf(
        ScreenShortcutData(
            title = "Current Trip",
            route = Screen.CurrentTrip.route
        ),
        ScreenShortcutData(
            title = "Add Expense",
            route = Screen.AddExpense.route
        ),
        ScreenShortcutData(
            title = "Past Trips",
            route = Screen.PastTrip.route
        ),
        ScreenShortcutData(
            title = "Login",
            route = Screen.LoginScreen.route
        )
    )
    private val _uiState =
        MutableStateFlow<HomeShortcutUiState>(
            HomeShortcutUiState.Success(
                searchQuery = "",
                shortcuts = allShortcuts
            )
        )
    val uiState =
        _uiState.asStateFlow()
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
        _uiState.value =
            HomeShortcutUiState.Success(
                searchQuery = "",
                shortcuts = allShortcuts
            )
    }
}