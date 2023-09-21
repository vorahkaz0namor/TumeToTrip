package ru.sign.conditional.timetotrip.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import ru.sign.conditional.timetotrip.R
import ru.sign.conditional.timetotrip.adapter.FlightViewHolder
import ru.sign.conditional.timetotrip.adapter.OnInteractionListenerImpl
import ru.sign.conditional.timetotrip.databinding.CardFlightBinding
import ru.sign.conditional.timetotrip.databinding.FragmentFeedFlightBinding
import ru.sign.conditional.timetotrip.dto.Flight
import ru.sign.conditional.timetotrip.util.CustomHelper.firstFlightSample
import ru.sign.conditional.timetotrip.util.CustomHelper.secondFlightSample
import ru.sign.conditional.timetotrip.util.viewBinding
import ru.sign.conditional.timetotrip.viewmodel.FlightViewModel

class FeedFlightFragment : Fragment(R.layout.fragment_feed_flight) {
    private val binding by viewBinding(FragmentFeedFlightBinding::bind)
    private val flightViewModel: FlightViewModel by activityViewModels()
    private val bindFlight = { cardBinding: CardFlightBinding, flight: Flight ->
        FlightViewHolder(
            binding = cardBinding,
            onInteractionListener = OnInteractionListenerImpl(flightViewModel)
        )
            .bind(flight)
    }
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        subscribe()
        setupListeners()
    }

    private fun init() {
        navController = findNavController()
    }

    private fun subscribe() {
        bindFlight(binding.firstSampleFlight, firstFlightSample)
        bindFlight(binding.secondSampleFlight, secondFlightSample)
    }

    private fun setupListeners() {
        binding.singleFlight.setOnClickListener {
            navController.navigate(
                R.id.action_feedFlightFragment_to_singleFlightFragment
            )
        }
    }
}