package ru.sign.conditional.timetotrip.dto

import java.time.Duration
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

data class Flight(
    override val searchToken: String,
    val startDate: String = "",
    val endDate: String = "",
    val startLocationCode: String = "",
    val endLocationCode: String = "",
    val startCity: String = "",
    val endCity: String = "",
    val price: Int = 0,
    val likedByMe: Boolean = false
): FeedItem {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z z")
    private val offsetDateTime = { input: String ->
        OffsetDateTime.parse(
            /* text = */ input,
            /* formatter = */ formatter
        )
    }
    private val validation = { input: String, action: String ->
        if (input.isNotBlank())
            action
        else
            ""
    }
    private val datePresentation = { input: String ->
        offsetDateTime(input)
            .format(DateTimeFormatter.ofPattern("dd MMM, E"))
    }
    private val timePresentation = { input: String ->
        offsetDateTime(input).toLocalTime().toString()
    }
    private val countFlightDuration = { startInput: String, endInput: String ->
        Duration.between(
            /* startInclusive = */ offsetDateTime(startInput),
            /* endExclusive = */ offsetDateTime(endInput)
        )
            .let(::FlightDuration)
    }
    val startDateToView: String = validation(startDate, datePresentation(startDate))
    val endDateToView: String = validation(endDate, datePresentation(endDate))
    val startTimeToView: String = validation(startDate, timePresentation(startDate))
    val endTimeToView: String = validation(endDate, timePresentation(endDate))
    val duration: FlightDuration = countFlightDuration(startDate, endDate)
}