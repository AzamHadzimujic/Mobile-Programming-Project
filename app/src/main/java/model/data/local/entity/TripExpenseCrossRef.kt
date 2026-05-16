package model.data.local.entity

import androidx.room.*

@Entity(
    tableName = "trip_expense_cross_ref",
    primaryKeys = ["trip_id", "expense_id"],
    foreignKeys = [
        ForeignKey(
            entity = PastTripEntity::class,
            parentColumns = ["id"],
            childColumns = ["trip_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExpenseEntity::class,
            parentColumns = ["id"],
            childColumns = ["expense_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("trip_id"),
        Index("expense_id")
    ]
)
data class TripExpenseCrossRef(
    @ColumnInfo(name = "trip_id")
    val tripId: Int,

    @ColumnInfo(name = "expense_id")
    val expenseId: Int
)