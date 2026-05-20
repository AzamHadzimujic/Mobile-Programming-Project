package presentation.viewmodel.home

import model.ScreenShortcutData

sealed interface HomeShortcutUiState {
    data object Init : HomeShortcutUiState
    data object Loading : HomeShortcutUiState
    data class Success(
        val searchQuery: String,
        val shortcuts: List<ScreenShortcutData>
    ) : HomeShortcutUiState
    data class Error(
        val message: String
    ) : HomeShortcutUiState
}