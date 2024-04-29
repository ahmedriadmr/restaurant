package com.doubleclick.rovleapp.core.interactor

import com.doubleclick.rovleapp.core.exception.Failure
import com.doubleclick.rovleapp.core.functional.Either
import com.doubleclick.rovleapp.core.platform.BaseViewModel
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
