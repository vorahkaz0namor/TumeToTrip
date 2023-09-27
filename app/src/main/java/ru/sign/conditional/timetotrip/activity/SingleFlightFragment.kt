package ru.sign.conditional.timetotrip.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.internal.http.HTTP_OK
import ru.sign.conditional.timetotrip.R
import ru.sign.conditional.timetotrip.adapter.FlightViewHolder
import ru.sign.conditional.timetotrip.adapter.OnInteractionListenerImpl
import ru.sign.conditional.timetotrip.databinding.FragmentSingleFlightBinding
import ru.sign.conditional.timetotrip.dto.Flight
import ru.sign.conditional.timetotrip.dto.Payload
import ru.sign.conditional.timetotrip.util.AndroidUtils.viewScope
import ru.sign.conditional.timetotrip.util.AndroidUtils.viewScopeWithRepeat
import ru.sign.conditional.timetotrip.util.CustomHelper.codeOverview
import ru.sign.conditional.timetotrip.util.viewBinding
import ru.sign.conditional.timetotrip.viewmodel.FlightViewModel

class SingleFlightFragment : Fragment(R.layout.fragment_single_flight) {
    private val binding by viewBinding(FragmentSingleFlightBinding::bind)
    private val flightViewModel: FlightViewModel by activityViewModels()
    private lateinit var bindFlight: (Flight?, Flight) -> Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        subscribe()
    }

    private fun init() {
        val holder = FlightViewHolder(
            binding = binding.singleFlight,
            onInteractionListener = OnInteractionListenerImpl(flightViewModel)
        )
        bindFlight = { oldItem: Flight?, newItem: Flight ->
            if (oldItem != newItem) {
                oldItem?.let {
                    val payload = Payload(
                        searchToken = newItem.searchToken,
                        likedByMe = newItem.likedByMe.takeIf {
                            it != oldItem.likedByMe
                        }
                    )
                    holder.bind(
                        payload = payload,
                        flight = newItem
                    )
                } ?: holder.bindSingleFlight(newItem)
            }
        }
    }

    private fun subscribe() {
        flightViewModel.apply {
            viewScopeWithRepeat {
                var oldItem: Flight? = null
                singleFlight.collectLatest { flight ->
                    flight?.let { newItem ->
                        bindFlight(oldItem, newItem)
                        oldItem = newItem
                    } ?: findNavController().navigateUp()
                }
            }
            viewScope.launch {
                flightOccurrence.observe(viewLifecycleOwner) { code ->
                    if (code != HTTP_OK) {
                        binding.apply {
                            singleFlight.root.isVisible = false
                            errorView.root.isVisible = true
                        }
                        Snackbar.make(
                            binding.root,
                            codeOverview(code),
                            Snackbar.LENGTH_INDEFINITE
                        )
                            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                            .setAction(android.R.string.ok) {
                                findNavController().navigateUp()
                            }
                            .show()
                    }
                }
            }
        }
    }
}