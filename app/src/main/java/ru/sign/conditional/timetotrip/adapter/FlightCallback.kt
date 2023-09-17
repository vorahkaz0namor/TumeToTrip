package ru.sign.conditional.timetotrip.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.sign.conditional.timetotrip.dto.Flight
import ru.sign.conditional.timetotrip.dto.Payload

class FlightCallback : DiffUtil.ItemCallback<Flight>() {
    override fun areItemsTheSame(oldItem: Flight, newItem: Flight): Boolean =
        oldItem.searchToken == newItem.searchToken

    override fun areContentsTheSame(oldItem: Flight, newItem: Flight): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: Flight, newItem: Flight): Any =
        Payload(
            searchToken = newItem.searchToken,
            likedByMe = newItem.likedByMe.takeIf { it != oldItem.likedByMe }
        )
}