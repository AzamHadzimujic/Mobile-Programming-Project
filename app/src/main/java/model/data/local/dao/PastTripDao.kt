package model.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import model.data.local.entity.PastTripEntity

@Dao
interface PastTripDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrip(trip: PastTripEntity)

    @Query("SELECT * FROM past_trips")
    fun getAllTrips(): Flow<List<PastTripEntity>>

    @Delete
    suspend fun deleteTrip(trip: PastTripEntity)
}