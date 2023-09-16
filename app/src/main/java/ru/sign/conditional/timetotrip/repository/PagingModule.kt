package ru.sign.conditional.timetotrip.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.sign.conditional.timetotrip.dao.FlightDao
import ru.sign.conditional.timetotrip.entity.FlightEntity
import ru.sign.conditional.timetotrip.remote.FlightRemoteApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PagingModule {
    @Singleton
    @Provides
    fun provideFlightRemoteMediator(
        flightRemoteApi: FlightRemoteApi,
        flightDao: FlightDao
    ) : FlightRemoteMediator = FlightRemoteMediator(
        flightRemoteApi = flightRemoteApi,
        flightDao = flightDao
    )

    @OptIn(ExperimentalPagingApi::class)
    @Singleton
    @Provides
    fun provideFlightPager(
        flightDao: FlightDao,
        flightRemoteMediator: FlightRemoteMediator
    ) : Pager<Int, FlightEntity> = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { flightDao.getPage() },
        remoteMediator = flightRemoteMediator
    )
}