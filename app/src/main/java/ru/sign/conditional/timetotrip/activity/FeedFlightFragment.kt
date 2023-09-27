package ru.sign.conditional.timetotrip.activity

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.internal.http.HTTP_OK
import ru.sign.conditional.timetotrip.R
import ru.sign.conditional.timetotrip.adapter.FlightAdapter
import ru.sign.conditional.timetotrip.adapter.OnInteractionListenerImpl
import ru.sign.conditional.timetotrip.databinding.FragmentFeedFlightBinding
import ru.sign.conditional.timetotrip.model.RemotePresentationState.*
import ru.sign.conditional.timetotrip.model.asRemotePresentationState
import ru.sign.conditional.timetotrip.util.AndroidUtils.viewScope
import ru.sign.conditional.timetotrip.util.AndroidUtils.viewScopeWithRepeat
import ru.sign.conditional.timetotrip.util.CustomHelper.HTTP_UNKNOWN_ERROR
import ru.sign.conditional.timetotrip.util.CustomHelper.codeOverview
import ru.sign.conditional.timetotrip.util.viewBinding
import ru.sign.conditional.timetotrip.viewmodel.FlightViewModel

class FeedFlightFragment : Fragment(R.layout.fragment_feed_flight) {
    private val binding by viewBinding(FragmentFeedFlightBinding::bind)
    private val flightViewModel: FlightViewModel by activityViewModels()
    private lateinit var flightAdapter: FlightAdapter
    private var snackbar: Snackbar? = null

    override fun onDestroyView() {
        snackbarDimiss()
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        subscribe()
    }

    private fun init() {
        flightAdapter =
            FlightAdapter(OnInteractionListenerImpl(flightViewModel))
        binding.recyclerView.root.adapter = flightAdapter
    }

    private fun subscribe() {
        flightViewModel.apply {
            viewScopeWithRepeat {
                dataFlow.collectLatest {
                    snackbarDimiss()
                    flightAdapter.submitData(it)
                }
            }
            viewScopeWithRepeat {
                flightAdapter.loadStateFlow
                    .asRemotePresentationState()
                    .collectLatest {
                        snackbarDimiss()
                        binding.apply {
                            progressBarView.root.isVisible = it != PRESENTED
                            recyclerView.root.isVisible = it == PRESENTED
                            emptyView.root.isVisible =
                                it == PRESENTED && flightAdapter.itemCount == 0
                        }
                    }
            }
            viewScope.launch {
                flightAdapter.loadStateFlow.collectLatest { loadState ->
                    snackbarDimiss()
                    val errorState = loadState.refresh as? LoadState.Error
                    binding.errorView.root.isVisible =
                            errorState is LoadState.Error
                    errorState?.let {
                        showSnackbar(it.error.message ?: codeOverview(HTTP_UNKNOWN_ERROR))
                    }
                }
            }
            viewScope.launch {
                flightOccurrence.observe(viewLifecycleOwner) { code ->
                    if (code != HTTP_OK) {
                        binding.apply {
                            recyclerView.root.isVisible = false
                            errorView.root.isVisible = true
                        }
                        showSnackbar(codeOverview(code))
                    }
                }
            }
            flightTokenToView.observe(viewLifecycleOwner) {
                if (it.isNotBlank()) {
                    findNavController().navigate(
                        R.id.action_feedFlightFragment_to_singleFlightFragment
                    )
                }
            }
        }
    }

    private fun showSnackbar(message: CharSequence) {
        snackbar = Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_INDEFINITE
        )
            .setTextMaxLines(3)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
            .setAction(R.string.retry_loading) {
                snackbarDimiss()
                flightAdapter.refresh()
            }
        snackbar?.show()
    }

    private fun snackbarDimiss() {
        if (snackbar != null && snackbar?.isShown == true) {
            snackbar?.dismiss()
            snackbar = null
        }
    }
}