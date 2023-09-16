package ru.sign.conditional.timetotrip.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.sign.conditional.timetotrip.dto.Flight

interface FlightRemoteApi {
    @POST("getCheap")
    suspend fun getFlights(
        @Body startLocationCode: String
    ): Response<List<Flight>>
}