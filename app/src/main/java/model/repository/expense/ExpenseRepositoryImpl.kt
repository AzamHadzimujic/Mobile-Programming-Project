package model.repository.expense

import kotlinx.coroutines.flow.Flow
import model.data.local.dao.ExpenseDao
import model.data.local.entity.ExpenseEntity

class ExpenseRepositoryImpl(
    private val dao: ExpenseDao
) : ExpenseRepository {
    override suspend fun insertExpense(expense: ExpenseEntity) {
        dao.insertExpense(expense)
    }
    override suspend fun getAllExpenses(): Flow<List<ExpenseEntity>> {
        return dao.getAllExpenses()
    }
    override suspend fun deleteExpense(expense: ExpenseEntity) {
        dao.deleteExpense(expense)
    }
}