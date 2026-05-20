package model.repository.pasttrip

import kotlinx.coroutines.flow.Flow
import model.data.local.dao.PastTripDao
import model.data.local.entity.PastTripEntity

class PastTripRepositoryImpl(
    private val dao: PastTripDao
) : PastTripRepository {
    override suspend fun insertTrip(trip: PastTripEntity) {
        dao.insertTrip(trip)
    }
    override fun getAllTrips(): Flow<List<PastTripEntity>> {
        return dao.getAllTrips()
    }
    override suspend fun deleteTrip(trip: PastTripEntity) {
        dao.deleteTrip(trip)
    }
}