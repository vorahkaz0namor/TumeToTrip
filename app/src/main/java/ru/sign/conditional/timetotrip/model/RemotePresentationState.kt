package ru.sign.conditional.timetotrip.model

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.scan
import ru.sign.conditional.timetotrip.model.RemotePresentationState.*

enum class RemotePresentationState {
    INITIAL,
    REMOTE_LOADING,
    SOURCE_LOADING,
    PRESENTED
}

fun Flow<CombinedLoadStates>.asRemotePresentationState() : Flow<RemotePresentationState> =
    scan(INITIAL) { state, loadState ->
        when (state) {
            INITIAL, PRESENTED ->
                when {
                    loadState.source.refresh is LoadState.Loading -> SOURCE_LOADING
                    loadState.mediator?.refresh is LoadState.Loading -> REMOTE_LOADING
                    loadState.source.refresh is LoadState.NotLoading &&
                    loadState.mediator?.refresh is LoadState.NotLoading -> PRESENTED
                    else -> state
                }
            REMOTE_LOADING ->
                when {
                    loadState.source.refresh is LoadState.Loading -> SOURCE_LOADING
                    loadState.mediator?.refresh is LoadState.NotLoading -> PRESENTED
                    else -> state
                }
            SOURCE_LOADING ->
                when (loadState.refresh) {
                    is LoadState.Loading -> REMOTE_LOADING
                    is LoadState.NotLoading -> PRESENTED
                    else -> state
                }
        }
    }
        .distinctUntilChanged()