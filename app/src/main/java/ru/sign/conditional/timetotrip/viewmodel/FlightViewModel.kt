package ru.sign.conditional.timetotrip.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.internal.http.HTTP_CONTINUE
import okhttp3.internal.http.HTTP_OK
import ru.sign.conditional.timetotrip.dto.FeedItem
import ru.sign.conditional.timetotrip.dto.Flight
import ru.sign.conditional.timetotrip.repository.FlightRepository
import ru.sign.conditional.timetotrip.util.AndroidUtils.defaultDispatcher
import ru.sign.conditional.timetotrip.util.CustomHelper.exceptionCode
import ru.sign.conditional.timetotrip.util.SingleLiveEvent
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class FlightViewModel @Inject constructor(
    private val flightRepository: FlightRepository
) : ViewModel() {
    private val cachedPagingDataFromRepo = flightRepository.data
        .mapLatest { it }
        .flowOn(defaultDispatcher)
        .distinctUntilChanged()
        .cachedIn(viewModelScope)
    val dataFlow: Flow<PagingData<FeedItem>>
        get() = cachedPagingDataFromRepo
    private var _singleFlight: Flow<Flight?> =
        flowOf(null).flowOn(defaultDispatcher)
    val singleFlight: Flow<Flight?>
        get() = _singleFlight
    private val _flightOccurrence = SingleLiveEvent(HTTP_CONTINUE)
    val flightOccurrence: LiveData<Int>
        get() = _flightOccurrence
    private val _flightTokenToView = MutableLiveData("")
    val flightTokenToView: LiveData<String>
        get() = _flightTokenToView

    fun getFlightByToken(token: String) {
        viewModelScope.launch {
            _flightOccurrence.value =
                try {
                    _flightTokenToView.value = token
                    _singleFlight = flightRepository.getFlightByToken(token)
                        .mapLatest { it }
                        .flowOn(defaultDispatcher)
                    _flightTokenToView.value = ""
                    HTTP_OK
                } catch (e: Exception) {
                    exceptionCode(e)
                }
        }
    }

    fun likeFlight(flight: Flight) {
        viewModelScope.launch {
            _flightOccurrence.value =
                try {
                    flightRepository.likeFlight(flight)
                    HTTP_OK
                } catch (e: Exception) {
                    exceptionCode(e)
                }
        }
    }
}