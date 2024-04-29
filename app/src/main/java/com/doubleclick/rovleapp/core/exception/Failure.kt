package com.doubleclick.rovleapp.core.exception

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    object NetworkConnection : Failure()
    class UnExpectedError(val message: String?) : Failure()
    object ServerError : Failure()

    object Authentication : Failure()

    /** * Extend this class for feature specific failures.*/
    class FeatureFailure(val message: String?) : Failure()
}
