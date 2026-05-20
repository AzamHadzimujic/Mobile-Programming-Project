package model.repository.expense

import kotlinx.coroutines.flow.Flow
import model.data.local.entity.ExpenseEntity

interface ExpenseRepository {
    suspend fun insertExpense(expense: ExpenseEntity)
    suspend fun getAllExpenses(): Flow<List<ExpenseEntity>>
    suspend fun deleteExpense(expense: ExpenseEntity)
}