package ru.sign.conditional.timetotrip.adapter

import ru.sign.conditional.timetotrip.dto.Flight

interface OnInteractionListener {
    fun onShowSingleFlight(flight: Flight)
    fun onLike(flight: Flight)
}