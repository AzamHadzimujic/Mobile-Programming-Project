package model.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import model.data.local.entity.ExpenseEntity

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)
}