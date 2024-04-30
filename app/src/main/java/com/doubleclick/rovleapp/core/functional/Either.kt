package com.doubleclick.restaurant.core.functional

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Failure] or [Success].
 * FP Convention dictates that [Failure] is used for "failure"
 * and [Success] is used for "success".
 *
 * @see Failure
 * @see Success
 */
sealed class Either<out L, out R> {
    /** * Represents the left side of [Either] class which by convention is a "Failure". */
    data class Failure<out L>(val a: L) : Either<L, Nothing>()

    /** * Represents the right side of [Either] class which by convention is a "Success". */
    data class Success<out R>(val b: R) : Either<Nothing, R>()

    data class Loading(val isLoading: Boolean) : Either<Nothing, Nothing>()


    /**
     * Returns true if this is a Right, false otherwise.
     * @see Success
     */
    val isSuccess get() = this is Success<R>

    /**
     * Returns true if this is a Left, false otherwise.
     * @see Failure
     */
    val isFailure get() = this is Failure<L>

    /**
     * Creates a Left type.
     * @see Failure
     */
    fun <L> left(a: L) = Failure(a)


    /**
     * Creates a Left type.
     * @see Success
     */
    fun <R> right(b: R) = Success(b)

    /**
     * Applies fnL if this is a Left or fnR if this is a Right.
     * @see Failure
     * @see Success
     */

    fun fold(fnL: (L) -> Any, fnR: (R) -> Any, fnLoading: (Boolean) -> Any = { }): Any =
        when (this) {
            is Success -> fnR(b)
            is Failure -> fnL(a)
            is Loading -> fnLoading
        }
}

/**
 * Composes 2 functions
 * See <a href="https://proandroiddev.com/kotlins-nothing-type-946de7d464fb">Credits to Alex Hart.</a>
 */
fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

/**
 * Right-biased flatMap() FP convention which means that Right is assumed to be the default case
 * to operate on. If it is Left, operations like map, flatMap, ... return the Left value unchanged.
 */


fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
    when (this) {
        is Either.Failure -> Either.Failure(a)
        is Either.Success -> fn(b)
        is Either.Loading -> Either.Loading(isLoading)
    }



/**
 * Right-biased map() FP convention which means that Right is assumed to be the default case
 * to operate on. If it is Left, operations like map, flatMap, ... return the Left value unchanged.
 */
fun <T, L, R> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> = this.flatMap(fn.c(::right))

/** Returns the value from this `Right` or the given argument if this is a `Left`.
 *  Right(12).getOrElse(17) RETURNS 12 and Left(12).getOrElse(17) RETURNS 17
 */
fun <L, R> Either<L, R>.getOrElse(value: R): R =
    when (this) {
        is Either.Failure -> value
        is Either.Success -> b
        is Either.Loading -> value
    }


/**
 * Left-biased onFailure() FP convention dictates that when this class is Left, it'll perform
 * the onFailure functionality passed as a parameter, but, overall will still return an either
 * object so you chain calls.
 */
fun <L, R> Either<L, R>.onFailure(fn: (failure: L) -> Unit): Either<L, R> =
    this.apply { if (this is Either.Failure) fn(a) }

/**
 * Right-biased onSuccess() FP convention dictates that when this class is Right, it'll perform
 * the onSuccess functionality passed as a parameter, but, overall will still return an either
 * object so you chain calls.
 */
fun <L, R> Either<L, R>.onSuccess(fn: (success: R) -> Unit): Either<L, R> =
    this.apply { if (this is Either.Success) fn(b) }
