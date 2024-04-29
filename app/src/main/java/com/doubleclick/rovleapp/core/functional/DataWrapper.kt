package com.doubleclick.rovleapp.core.functional

import com.doubleclick.rovleapp.feature.auth.signup.data.response.ErrorsSignUp


abstract class ModelWrapper
data class DataWrapper<out T>(
    val data: T,
    val message: String = "",
    val errors: ErrorsSignUp? = ErrorsSignUp(emptyList(), emptyList()),
    val token: String? = null,
) : ModelWrapper()