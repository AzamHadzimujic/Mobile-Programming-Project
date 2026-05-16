package model.repository.pasttrip

import kotlinx.coroutines.flow.Flow
import model.data.local.entity.PastTripEntity

interface PastTripRepository {
    suspend fun insertTrip(trip: PastTripEntity)
    fun getAllTrips(): Flow<List<PastTripEntity>>
    suspend fun deleteTrip(trip: PastTripEntity)
}