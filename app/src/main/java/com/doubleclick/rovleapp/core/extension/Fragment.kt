package com.doubleclick.rovleapp.core.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.doubleclick.rovleapp.core.exception.Failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



fun <T : Any, L : StateFlow<T>> Fragment.observe(flow: L, body: (T) -> Unit) =
    viewLifecycleOwner.lifecycleScope.launch {
        flow.flowWithLifecycle(this@observe.lifecycle).collect(body)
    }

/** success one time event */
fun <T : Any, L : Flow<T>> Fragment.observe(flow: L, body: (T) -> Unit) =
    viewLifecycleOwner.lifecycleScope.launch {
        flow.flowWithLifecycle(this@observe.lifecycle).collect(body)
    }


fun <T : Any, L : Flow<T?>> Fragment.observeOrNull(flow: L?, body: (T?) -> Unit) =
    viewLifecycleOwner.lifecycleScope.launch {
        flow?.flowWithLifecycle(this@observeOrNull.lifecycle)?.collect(body)
    }


/** failure one time event */
fun <L : Flow<Failure?>> Fragment.failure(flow: L, body: (Failure?) -> Unit) =
    viewLifecycleOwner.lifecycleScope.launch {
        flow.flowWithLifecycle(this@failure.lifecycle).collect(body)
    }


fun Fragment.findNavControllerFromFragmentContainer(id: Int): NavController {
    val fragment = childFragmentManager.findFragmentById(id)
    check(fragment is NavHostFragment) { ("Activity $this does not have a NavHostFragment") }
    return fragment.navController
}