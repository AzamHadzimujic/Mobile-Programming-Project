package model.data.local.dao

import androidx.room.*
import model.data.local.entity.TripExpenseCrossRef

@Dao
interface TripExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRelation(relation: TripExpenseCrossRef)

    @Update
    suspend fun updateExpense(expense: TripExpenseCrossRef)

    @Query("SELECT * FROM trip_expense_cross_ref")
    suspend fun getAllRelations(): List<TripExpenseCrossRef>
}