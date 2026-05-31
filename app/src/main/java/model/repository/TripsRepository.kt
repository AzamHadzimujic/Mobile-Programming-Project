package model.repository
import model.data.local.entity.PastTripEntity
import model.data.remote.api.TripsApi
import model.data.remote.dto.PastTripDto

class TripsRepository(
    private val api: TripsApi
) {
    suspend fun getTripsFromApi(): List<PastTripEntity> {
        return api.getTrips().map {
            PastTripEntity(
                id = it.id,
                title = it.title,
                categories = it.categories,
                amount = it.amount,
                date = it.date
            )
        }
    }
    suspend fun addTripToApi(
        title: String,
        category: String,
        amount: String,
        date: String
    ) {
        api.createTrip(
            PastTripDto(
                title = title,
                categories = category,
                amount = amount,
                date = date
            )
        )
    }
    suspend fun deleteTripFromApi(id: Int) {
        api.deleteTrip(id)
    }
    suspend fun updateTripToApi(
        id: Int,
        title: String,
        category: String,
        amount: String,
        date: String
    ) {
        api.updateTrip(
            id,
            PastTripDto(
                id = id,
                title = title,
                categories = category,
                amount = amount,
                date = date
            )
        )
    }
}