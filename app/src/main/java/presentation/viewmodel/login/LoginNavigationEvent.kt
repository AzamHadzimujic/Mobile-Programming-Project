package presentation.viewmodel.login
sealed interface LoginNavigationEvent{
    object Navigate : LoginNavigationEvent;
    object NavigateBack : LoginNavigationEvent;
}