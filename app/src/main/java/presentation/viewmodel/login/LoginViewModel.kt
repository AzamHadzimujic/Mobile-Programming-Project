package presentation.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import model.data.local.entity.UserEntity
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
            var user = userRepository.getUser(username, password)
            if (user == null) {
                val newUser = UserEntity(
                    id = 0,
                    username = username,
                    password = password
                )
                userRepository.insertUser(newUser)
                user = userRepository.getUser(username, password)
            }
            user?.let {
                sessionRepository.saveUserId(it.id)
                _navigationEvent.send(LoginNavigationEvent.Navigate)
            }
        }
    }
    fun resetUiState() {
        _uiState.value = LoginUiState.Init
    }
}