package presentation.viewmodel.auth.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RegistrationViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<RegistrationUiState>(RegistrationUiState.Init)
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<RegistrationNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onRegisterClick(
        fullName: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _uiState.value = RegistrationUiState.Loading

            delay(1500)

            if (fullName.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                _uiState.value = RegistrationUiState.Success
                _navigationEvent.send(RegistrationNavigationEvent.Navigate)
            } else {
                _uiState.value = RegistrationUiState.Error("Please fill in all fields.")
            }
        }
    }

    fun resetUiState() {
        _uiState.value = RegistrationUiState.Init
    }
}