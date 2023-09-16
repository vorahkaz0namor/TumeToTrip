package ru.sign.conditional.timetotrip.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import ru.sign.conditional.timetotrip.dao.FlightDao
import ru.sign.conditional.timetotrip.dto.Flight
import ru.sign.conditional.timetotrip.entity.FlightEntity
import ru.sign.conditional.timetotrip.util.AndroidUtils.defaultDispatcher
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class FlightRepositoryImpl @Inject constructor(
    private val flightDao: FlightDao,
    private val flightPager: Pager<Int, FlightEntity>
): FlightRepository {
    override val data: Flow<PagingData<Flight>>
        get() = flightPager.flow
            .mapLatest {
                it.map(FlightEntity::toDto)
            }

    override suspend fun getFlightByToken(token: String): Flow<Flight?> =
        flightDao.getFlightByToken(token)
            .mapLatest {
                it?.toDto()
            }
            .flowOn(defaultDispatcher)
}