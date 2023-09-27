package ru.sign.conditional.timetotrip.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object AndroidUtils {
    val defaultDispatcher = Dispatchers.Default
    val Fragment.viewScope
        get() = viewLifecycleOwner.lifecycleScope

    fun Fragment.viewScopeWithRepeat(
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                block()
            }
        }
    }
}