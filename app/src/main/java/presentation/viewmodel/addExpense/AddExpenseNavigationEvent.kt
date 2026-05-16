package presentation.viewmodel.addExpense
sealed interface AddExpenseNavigationEvent{
    object Navigate : AddExpenseNavigationEvent;
    object NavigateBack : AddExpenseNavigationEvent;
}