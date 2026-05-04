package presentation.viewmodel.auth.registration

sealed interface RegistrationUiState {
    data object Init : RegistrationUiState
    data object Loading : RegistrationUiState
    data object Success : RegistrationUiState
    data class Error(val message: String) : RegistrationUiState
}