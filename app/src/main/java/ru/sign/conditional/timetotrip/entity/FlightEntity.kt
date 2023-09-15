package ru.sign.conditional.timetotrip.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.sign.conditional.timetotrip.dto.Flight

@Entity
data class FlightEntity(
    @PrimaryKey
    val searchToken: String,
    val startDate: String,
    val endDate: String,
    val startCity: String,
    val endCity: String,
    val price: Int
) {
    fun toDto() = Flight(
        searchToken = searchToken,
        startDate = startDate,
        endDate = endDate,
        startCity = startCity,
        endCity = endCity,
        price = price
    )

    companion object {
        fun fromDto(dtoFlight: Flight) =
            FlightEntity(
                searchToken = dtoFlight.searchToken,
                startDate = dtoFlight.startDate,
                endDate = dtoFlight.endDate,
                startCity = dtoFlight.startCity,
                endCity = dtoFlight.endCity,
                price = dtoFlight.price
            )
    }
}