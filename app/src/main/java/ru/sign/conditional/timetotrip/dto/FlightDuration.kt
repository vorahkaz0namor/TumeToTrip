package ru.sign.conditional.timetotrip.dto

import java.time.Duration

data class FlightDuration(
    private val duration: Duration
) {
    val days = duration.toDays()
    val hours = duration.toHours() - 24 * days
    val minutes = duration.toMinutes() - 60 * duration.toHours()
}
