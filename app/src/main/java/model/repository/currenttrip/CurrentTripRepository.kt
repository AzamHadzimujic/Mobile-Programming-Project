package model.repository.currenttrip

import kotlinx.coroutines.flow.Flow
import model.data.local.entity.TripEntity

interface TripRepository {
    suspend fun insertTrip(
        trip: TripEntity
    )
    suspend fun updateTrip(
        trip: TripEntity
    )
    suspend fun deleteTrip(
        trip: TripEntity
    )
    fun getAllTrips():
            Flow<List<TripEntity>>
    suspend fun getTripById(
        id: Int
    ): TripEntity?
}