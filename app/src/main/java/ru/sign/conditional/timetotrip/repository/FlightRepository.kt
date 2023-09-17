package ru.sign.conditional.timetotrip.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.sign.conditional.timetotrip.dto.FeedItem
import ru.sign.conditional.timetotrip.dto.Flight

interface FlightRepository {
    val data: Flow<PagingData<FeedItem>>
    suspend fun getFlightByToken(token: String): Flow<Flight?>
    suspend fun likeFlight(flight: Flight)
}