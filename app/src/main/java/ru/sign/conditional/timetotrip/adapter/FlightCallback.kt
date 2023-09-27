package ru.sign.conditional.timetotrip.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.sign.conditional.timetotrip.dto.FeedItem
import ru.sign.conditional.timetotrip.dto.Flight
import ru.sign.conditional.timetotrip.dto.Payload

class FlightCallback : DiffUtil.ItemCallback<FeedItem>() {
    override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean =
        oldItem.searchToken == newItem.searchToken

    override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: FeedItem, newItem: FeedItem): Any =
        Payload(
            searchToken = newItem.searchToken,
            likedByMe = (newItem as? Flight)?.likedByMe.takeIf {
                it != (oldItem as? Flight)?.likedByMe
            }
        )
}