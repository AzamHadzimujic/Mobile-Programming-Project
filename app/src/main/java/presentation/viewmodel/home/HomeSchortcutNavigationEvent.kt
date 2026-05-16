package presentation.viewmodel.home

sealed interface HomeShortcutNavigationEvent {
    data object Navigate : HomeShortcutNavigationEvent
    data object NavigateBack : HomeShortcutNavigationEvent
}