package ru.sign.conditional.timetotrip.dao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.sign.conditional.timetotrip.db.AppDb
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DaoModule {
    @Singleton
    @Provides
    fun provideFlightDao(
        appDb: AppDb
    ) : FlightDao = appDb.flightDao()
}