package model.repository.currenttrip

import kotlinx.coroutines.flow.Flow
import model.data.local.dao.TripDao
import model.data.local.entity.TripEntity
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    private val dao: TripDao
) : TripRepository {
    override suspend fun insertTrip(
        trip: TripEntity
    ) {
        dao.insertTrip(trip)
    }
    override suspend fun updateTrip(
        trip: TripEntity
    ) {
        dao.updateTrip(trip)
    }
    override suspend fun deleteTrip(
        trip: TripEntity
    ) {
        dao.deleteTrip(trip)
    }
    override fun getAllTrips():
            Flow<List<TripEntity>> {
        return dao.getAllTrips()
    }
    override suspend fun getTripById(
        id: Int
    ): TripEntity? {
        return dao.getTripById(id)
    }
}