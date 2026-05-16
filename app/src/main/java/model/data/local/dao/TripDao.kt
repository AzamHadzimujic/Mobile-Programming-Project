package model.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import model.data.local.entity.TripEntity

@Dao
interface TripDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrip(
        trip: TripEntity
    )

    @Update
    suspend fun updateTrip(
        trip: TripEntity
    )

    @Delete
    suspend fun deleteTrip(
        trip: TripEntity
    )

    @Query("SELECT * FROM trips")
    fun getAllTrips(): Flow<List<TripEntity>>

    @Query("SELECT * FROM trips WHERE id = :id")
    suspend fun getTripById(
        id: Int
    ): TripEntity?
}