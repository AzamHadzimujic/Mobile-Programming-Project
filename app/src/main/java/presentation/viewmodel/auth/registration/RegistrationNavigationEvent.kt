package presentation.viewmodel.auth.registration

sealed interface RegistrationNavigationEvent {
    data object Navigate : RegistrationNavigationEvent
    data object NavigateBack : RegistrationNavigationEvent
}