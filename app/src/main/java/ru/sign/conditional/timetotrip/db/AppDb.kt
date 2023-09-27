package ru.sign.conditional.timetotrip.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.sign.conditional.timetotrip.dao.FlightDao
import ru.sign.conditional.timetotrip.entity.FlightEntity

@Database(
    entities = [FlightEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract fun flightDao(): FlightDao
}