package ru.sign.conditional.timetotrip.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import ru.sign.conditional.timetotrip.R
import ru.sign.conditional.timetotrip.databinding.FragmentFeedFlightBinding
import ru.sign.conditional.timetotrip.util.viewBinding

class FeedFlightFragment : Fragment(R.layout.fragment_feed_flight) {
    private val binding by viewBinding(FragmentFeedFlightBinding::bind)
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.singleFlight.setOnClickListener {
            navController.navigate(
                R.id.action_feedFlightFragment_to_singleFlightFragment
            )
        }
    }
}