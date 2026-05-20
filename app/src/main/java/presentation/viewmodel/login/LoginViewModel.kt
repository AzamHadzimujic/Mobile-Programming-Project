package presentation.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import model.repository.user.SessionRepository
import model.repository.user.UserRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Init)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    private val _navigationEvent = Channel<LoginNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()
    fun onLoginClick(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                val user = userRepository.getUser(username, password)
                if (user != null) {
                    sessionRepository.saveUserId(user.id)
                    _uiState.value = LoginUiState.Success(isLoggedIn = true)
                    _navigationEvent.send(LoginNavigationEvent.Navigate)
                } else {
                    _uiState.value =
                        LoginUiState.Error("Invalid username or password")
                }
            } catch (e: Throwable) {
                _uiState.value =
                    LoginUiState.Error("Something went wrong: ${e.message}")
            }
        }
    }
    fun resetUiState() {
        _uiState.value = LoginUiState.Init
    }
}