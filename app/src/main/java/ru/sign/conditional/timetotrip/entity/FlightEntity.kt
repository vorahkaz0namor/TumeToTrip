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
    val startLocationCode: String = "",
    val endLocationCode: String = "",
    val startCity: String,
    val endCity: String,
    val price: Int,
    val likedByMe: Boolean
) {
    fun toDto() = Flight(
        searchToken = searchToken,
        startDate = startDate,
        endDate = endDate,
        startLocationCode = startLocationCode,
        endLocationCode = endLocationCode,
        startCity = startCity,
        endCity = endCity,
        price = price,
        likedByMe = likedByMe
    )

    companion object {
        fun fromDto(dtoFlight: Flight) =
            FlightEntity(
                searchToken = dtoFlight.searchToken,
                startDate = dtoFlight.startDate,
                endDate = dtoFlight.endDate,
                startLocationCode = dtoFlight.startLocationCode,
                endLocationCode = dtoFlight.endLocationCode,
                startCity = dtoFlight.startCity,
                endCity = dtoFlight.endCity,
                price = dtoFlight.price,
                likedByMe = dtoFlight.likedByMe
            )
    }
}