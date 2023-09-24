package ru.sign.conditional.timetotrip.repository

import androidx.paging.*
import androidx.paging.LoadType.*
import retrofit2.HttpException
import ru.sign.conditional.timetotrip.dao.FlightDao
import ru.sign.conditional.timetotrip.dto.Flight
import ru.sign.conditional.timetotrip.dto.StartLocationCode
import ru.sign.conditional.timetotrip.entity.FlightEntity
import ru.sign.conditional.timetotrip.remote.FlightRemoteApi

@OptIn(ExperimentalPagingApi::class)
class FlightRemoteMediator(
    private val flightRemoteApi: FlightRemoteApi,
    private val flightDao: FlightDao
) : RemoteMediator<Int, FlightEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FlightEntity>
    ): MediatorResult {
        try {
            val response = when (loadType) {
                REFRESH -> {
                    flightRemoteApi.getFlights(
                        StartLocationCode("LED")
                    )
                }
                else -> {
                    return MediatorResult.Success(false)
                }
            }
            if (response.isSuccessful) {
                response.body()?.flights?.let {
                    if (it.isNotEmpty())
                        saveFlightsFromServer(it)
                    return MediatorResult.Success(endOfPaginationReached = it.isEmpty())
                } ?: throw HttpException(response)
            } else
                throw HttpException(response)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun saveFlightsFromServer(flights: List<Flight>) {
        var newFlights: List<FlightEntity> = emptyList()
        flightDao.getAll().apply {
            flights.map { singleFlight ->
                val existingFlight = find {
                    it.searchToken == singleFlight.searchToken
                }
                if (existingFlight == null)
                    newFlights = newFlights.plus(
                        FlightEntity.fromDto(singleFlight)
                    )
            }
        }
        flightDao.saveFlights(newFlights)
    }
}