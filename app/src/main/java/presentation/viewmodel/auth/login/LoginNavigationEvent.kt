package presentation.viewmodel.auth.login

sealed interface LoginNavigationEvent{
    object Navigate : LoginNavigationEvent;
    object NavigateBack : LoginNavigationEvent;
}