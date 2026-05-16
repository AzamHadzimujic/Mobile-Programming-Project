package presentation.viewmodel.auth
sealed interface LoginNavigationEvent{
    object Navigate : LoginNavigationEvent;
    object NavigateBack : LoginNavigationEvent;
}