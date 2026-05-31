package model.data.remote.api

import model.data.remote.dto.PastTripDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TripsApi {

    @GET("trips/")
    suspend fun getTrips(): List<PastTripDto>

    @POST("trips/")
    suspend fun createTrip(
        @Body trip: PastTripDto
    ): PastTripDto

    @DELETE("trips/{id}")
    suspend fun deleteTrip(
        @Path("id") id: Int
    )

    @PUT("trips/{id}")
    suspend fun updateTrip(
        @Path("id") id: Int,
        @Body trip: PastTripDto
    ): PastTripDto
}