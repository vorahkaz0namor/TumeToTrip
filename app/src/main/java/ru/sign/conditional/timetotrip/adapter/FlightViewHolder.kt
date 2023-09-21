package ru.sign.conditional.timetotrip.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.sign.conditional.timetotrip.R
import ru.sign.conditional.timetotrip.databinding.CardFlightBinding
import ru.sign.conditional.timetotrip.dto.Flight
import ru.sign.conditional.timetotrip.dto.Payload

class FlightViewHolder(
    private val binding: CardFlightBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(flight: Flight) {
        binding.apply {
            departurePoint.apply {
                date.text = flight.startDateToView
                time.text = flight.startTimeToView
                city.apply {
                    text = context.getString(
                        R.string.point_code_and_city,
                        flight.startLocationCode,
                        flight.startCity
                    )
                }
            }
            flightDuration.apply {
                text = buildString {
                    if (flight.duration.days != 0L)
                        append(
                            context.getString(
                                R.string.days_count,
                                flight.duration.days.toString()
                            )
                        )
                    else
                        append("")
                    if (flight.duration.hours != 0L)
                        append(
                            context.getString(
                                R.string.hours_count,
                                flight.duration.hours.toString()
                            )
                        )
                    else
                        append("")
                    append(
                        context.getString(
                            R.string.flight_duration,
                            flight.duration.minutes.toString()
                        )
                    )
                }
            }
            destination.apply {
                date.text = flight.endDateToView
                time.text = flight.endTimeToView
                city.apply {
                    text = context.getString(
                        R.string.point_code_and_city,
                        flight.endLocationCode,
                        flight.endCity
                    )
                }
            }
            price.text = flight.price.toString()
        }
    }

    fun bind(payload: Payload, flight: Flight?) {

    }
}