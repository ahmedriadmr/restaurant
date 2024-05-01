package com.doubleclick.restaurant.core.interactor

import com.doubleclick.restaurant.core.exception.Failure
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.platform.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<out Type : Any, in Params> {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(
        params: Params,
        scope: CoroutineScope,
        loading: BaseViewModel? = null,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
        scope.launch {
            loading?.handleLoading(Either.Loading(true))
            val deferred = async { run(params) }
            onResult(deferred.await().also { loading?.handleLoading(Either.Loading(false)) })
        }
    }

    class None
}
