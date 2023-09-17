package ru.sign.conditional.timetotrip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.sign.conditional.timetotrip.R
import ru.sign.conditional.timetotrip.databinding.CardFlightBinding
import ru.sign.conditional.timetotrip.dto.Flight
import ru.sign.conditional.timetotrip.dto.Payload

class FlightAdapter(
    private val onInteractionListener: OnInteractionListener
) : PagingDataAdapter<Flight, RecyclerView.ViewHolder>(FlightCallback()) {
    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is Flight -> R.layout.card_flight
            else -> error("Unknown item type")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is Flight -> (holder as? FlightViewHolder)?.bind(item)
            null -> getItemViewType(position)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        if (payloads.isNotEmpty()) {
            payloads.map {
                (it as? Payload)?.let { payload ->
                    (holder as? FlightViewHolder)?.let { flightViewHolder ->
                        val updatedFlight = getItem(position)?.let { flight ->
                            flight.copy(likedByMe = payload.likedByMe ?: flight.likedByMe)
                        }
                        flightViewHolder.bind(payload, updatedFlight)
                    }
                }
            }
        } else
            onBindViewHolder(
                holder = holder,
                position = position
            )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.card_flight ->
                FlightViewHolder(
                    CardFlightBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onInteractionListener
                )
            else -> error("Unknown view type: $viewType")
        }
}