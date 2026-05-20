package presentation.viewmodel.addExpense
sealed interface AddExpenseNavigationEvent{
    object NavigateBack : AddExpenseNavigationEvent;
}