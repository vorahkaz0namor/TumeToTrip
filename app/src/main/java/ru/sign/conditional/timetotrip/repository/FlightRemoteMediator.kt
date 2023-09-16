package ru.sign.conditional.timetotrip.repository

import androidx.paging.*
import androidx.paging.LoadType.*
import retrofit2.HttpException
import ru.sign.conditional.timetotrip.dao.FlightDao
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
                    flightRemoteApi.getFlights("LED")
                }
                else -> {
                    return MediatorResult.Success(false)
                }
            }
            if (response.isSuccessful) {
                val body = response.body()
                    ?: throw HttpException(response)
                if (body.isNotEmpty())
                    flightDao.saveFlights(
                        body.map(FlightEntity.Companion::fromDto)
                    )
                return MediatorResult.Success(endOfPaginationReached = body.isEmpty())
            } else
                throw HttpException(response)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}