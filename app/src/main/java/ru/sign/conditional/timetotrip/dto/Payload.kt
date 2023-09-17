package ru.sign.conditional.timetotrip.dto

data class Payload(
    override val searchToken: String,
    val likedByMe: Boolean? = null
): FeedItem
