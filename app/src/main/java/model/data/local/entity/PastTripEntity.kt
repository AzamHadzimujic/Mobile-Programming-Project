package model.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "past_trips")
data class PastTripEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val categories: String,
    val amount: String,
    val date: String
)