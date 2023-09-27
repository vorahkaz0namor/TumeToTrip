package ru.sign.conditional.timetotrip.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.sign.conditional.timetotrip.entity.FlightEntity

@Dao
interface FlightDao {
    @Query("SELECT * FROM FlightEntity ORDER BY startDate ASC")
    fun getPage(): PagingSource<Int, FlightEntity>

    @Query("SELECT * FROM FlightEntity")
    suspend fun getAll(): List<FlightEntity>

    @Query("SELECT * FROM FlightEntity WHERE searchToken = :token")
    fun getFlightByToken(token: String): Flow<FlightEntity?>

    @Insert(onConflict = REPLACE)
    suspend fun saveFlights(flights: List<FlightEntity>)
}