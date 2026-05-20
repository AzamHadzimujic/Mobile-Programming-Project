package presentation.viewmodel.addExpense

data class AddExpenseFormState(
    val title: String = "",
    val amount: String = "",
    val category: String = ""
)