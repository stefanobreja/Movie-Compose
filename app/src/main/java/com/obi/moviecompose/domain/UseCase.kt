package com.obi.moviecompose.domain

import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import timber.log.Timber

abstract class UseCase<out T, in Params> {
    abstract suspend fun get(params: Params? = null): T
    suspend operator fun invoke(params: Params? = null): Result<T> = supervisorScope {
        val backgroundJob = async {
            kotlin.runCatching {
                get(params)
            }
        }
        var result: Result<T> = backgroundJob.await()
            .onFailure { failure ->
                val failureCause = failure.cause
                Timber.w(failureCause, "${this@UseCase.javaClass} failed")
            }
        val ex = result.exceptionOrNull()
        if (ex != null) {
            result = Result.failure(ex)
        }
        result
    }
}