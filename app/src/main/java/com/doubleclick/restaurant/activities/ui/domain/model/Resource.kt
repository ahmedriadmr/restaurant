package com.doubleclick.domain.model

sealed class Resource<T>(
    val data: T? = null,
    val errorMessage: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(errorMessage: String, data: T? = null) : Resource<T>(data , errorMessage)
}