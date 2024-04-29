package com.doubleclick.rovleapp.core.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.doubleclick.rovleapp.core.exception.Failure
import com.doubleclick.rovleapp.core.functional.Either
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T) -> Unit) =
    liveData.observe(this, Observer(body))


fun <T : Any, L : StateFlow<T>> LifecycleOwner.observe(flow: L, body: (T) -> Unit) =
    this.lifecycleScope.launch { flow.flowWithLifecycle(this@observe.lifecycle).collect(body) }

fun <T : Any, L : Flow<T?>> LifecycleOwner.observeOrNull(flow: L?, body: (T?) -> Unit) =
    this.lifecycleScope.launch { flow?.flowWithLifecycle(this@observeOrNull.lifecycle)?.collect(body) }

fun <L : Flow<Either.Loading>> LifecycleOwner.loading(flow: L, body: (Either.Loading) -> Unit) =
    this.lifecycleScope.launch { flow.flowWithLifecycle(this@loading.lifecycle).collect(body) }

/** success one time event */
fun <T : Any, L : Flow<T>> LifecycleOwner.observe(flow: L, body: (T) -> Unit) =
    this.lifecycleScope.launch { flow.flowWithLifecycle(this@observe.lifecycle).collect(body) }

/** failure one time event */
fun <L : Flow<Failure?>> LifecycleOwner.failure(flow: L, body: (Failure?) -> Unit) =
    this.lifecycleScope.launch { flow.flowWithLifecycle(this@failure.lifecycle).collect(body) }


