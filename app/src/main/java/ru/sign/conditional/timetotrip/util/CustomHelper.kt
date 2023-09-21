package ru.sign.conditional.timetotrip.util

import retrofit2.HttpException
import ru.sign.conditional.timetotrip.dto.Flight
import java.net.ConnectException

object CustomHelper {
    /** `520 Unknown Error` (non-standard HTTP code CloudFlare) */
    private const val HTTP_UNKNOWN_ERROR = 520
    /** `444 Connection Failed` (thought up code) */
    private const val HTTP_CONNECTION_FAILED = 444
    /** Definition of the exception code */
    val exceptionCode = { e: Exception ->
        when (e) {
            is HttpException -> e.code()
            is ConnectException -> HTTP_CONNECTION_FAILED
            else -> HTTP_UNKNOWN_ERROR
        }
    }
    val firstFlightSample = Flight(
        searchToken = "LED210923SVOY100",
        startDate = "2023-09-21 14:50:00 +0000 UTC",
        endDate = "2023-09-21 16:15:00 +0000 UTC",
        startLocationCode = "LED",
        endLocationCode = "SVO",
        startCity = "Санкт-Петербург",
        endCity = "Москва",
        price = 3470
    )
    val secondFlightSample = Flight(
        searchToken = "LED210923ORDY100",
        startDate = "2023-09-21 13:15:00 +0000 UTC",
        endDate = "2023-09-23 17:50:00 +0000 UTC",
        startLocationCode = "LED",
        endLocationCode = "ORD",
        startCity = "Санкт-Петербург",
        endCity = "Чикаго",
        price = 154955
    )
}