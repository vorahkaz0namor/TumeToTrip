package ru.sign.conditional.timetotrip.adapter

import ru.sign.conditional.timetotrip.dto.Flight
import ru.sign.conditional.timetotrip.viewmodel.FlightViewModel

class OnInteractionListenerImpl(
    private val flightViewModel: FlightViewModel
) : OnInteractionListener {
    override fun onShowSingleFlight(flight: Flight) {
        flightViewModel.getFlightByToken(flight.searchToken)
    }

    override fun onLike(flight: Flight) {
        flightViewModel.likeFlight(flight)
    }
}