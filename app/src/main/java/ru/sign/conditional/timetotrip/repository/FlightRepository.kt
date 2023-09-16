package ru.sign.conditional.timetotrip.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.sign.conditional.timetotrip.dto.Flight

interface FlightRepository {
    val data: Flow<PagingData<Flight>>
    suspend fun getFlightByToken(token: String): Flow<Flight?>
}