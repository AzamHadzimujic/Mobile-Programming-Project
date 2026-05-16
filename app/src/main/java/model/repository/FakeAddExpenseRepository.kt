package model.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import model.ExpenseItem

class FakeAddExpenseRepository {
    suspend fun getExpense(shouldFail: Boolean = false): ExpenseItem = withContext(Dispatchers.IO) {
        delay(2000)
        if (shouldFail) {
            throw IllegalStateException("Failed to load profile.")
        }
        ExpenseItem(
            id = "1",
            title = "Hotel",
            amount = "250",
            categories = "Travel"
        )
    }
}