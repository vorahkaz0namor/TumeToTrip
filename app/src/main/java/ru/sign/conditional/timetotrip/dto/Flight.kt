package ru.sign.conditional.timetotrip.dto

data class Flight(
    val searchToken: String,
    val startDate: String = "",
    val endDate: String = "",
    val startCity: String = "",
    val endCity: String = "",
    val price: Int = 0
)
