package model.data.local.db


import androidx.room.Database
import androidx.room.RoomDatabase
import model.data.local.dao.ExpenseDao
import model.data.local.dao.PastTripDao
import model.data.local.dao.TripDao
import model.data.local.entity.ExpenseEntity
import model.data.local.entity.PastTripEntity
import model.data.local.entity.TripExpenseCrossRef
import model.data.local.entity.UserEntity
import model.data.local.entity.TripEntity
import model.data.local.dao.TripExpenseDao
import model.data.local.dao.UserDao

@Database(
    entities = [
        ExpenseEntity::class,
        PastTripEntity::class,
        TripExpenseCrossRef::class,
        UserEntity::class,
        TripEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun pastTripDao(): PastTripDao
    abstract fun tripExpenseDao(): TripExpenseDao
    abstract fun userDao(): UserDao
    abstract fun tripDao(): TripDao
}