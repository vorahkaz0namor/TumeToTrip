package ru.sign.conditional.timetotrip.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.sign.conditional.timetotrip.databinding.CardFlightBinding
import ru.sign.conditional.timetotrip.dto.Flight
import ru.sign.conditional.timetotrip.dto.Payload

class FlightViewHolder(
    private val binding: CardFlightBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(flight: Flight) {

    }

    fun bind(payload: Payload, flight: Flight?) {

    }
}