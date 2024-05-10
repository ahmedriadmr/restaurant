package com.doubleclick.restaurant.feature.auth


import androidx.lifecycle.viewModelScope
import com.doubleclick.restaurant.core.platform.BaseViewModel
import com.doubleclick.restaurant.core.platform.local.AppSettingsSource
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.ForgetPasswordResponse
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.request.forgetPassword.ForgetPasswordRequest
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.request.resetPassword.ResetPasswordRequest
import com.doubleclick.restaurant.feature.auth.forgetPassword.data.request.verifyOtp.VerifyOtpRequest
import com.doubleclick.restaurant.feature.auth.forgetPassword.domain.ForgetPasswordUseCase
import com.doubleclick.restaurant.feature.auth.forgetPassword.domain.ResetPasswordUseCase
import com.doubleclick.restaurant.feature.auth.forgetPassword.domain.VerifyOtpUseCase
import com.doubleclick.restaurant.feature.auth.login.data.request.LoginRequest
import com.doubleclick.restaurant.feature.auth.login.data.responseNew.NewUser
import com.doubleclick.restaurant.feature.auth.login.domain.LoginUseCase
import com.doubleclick.restaurant.feature.auth.signup.data.request.SignUpRequest
import com.doubleclick.restaurant.feature.auth.signup.data.responseNew.SignedUpUser
import com.doubleclick.restaurant.feature.auth.signup.domain.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val signUpUseCase: SignUpUseCase,
    val loginUseCase: LoginUseCase,
    val forgetPasswordUseCase: ForgetPasswordUseCase,
    val verifyOtpUseCase: VerifyOtpUseCase,
    val resetPasswordUseCase: ResetPasswordUseCase,
    val appSettingsSource: AppSettingsSource
) : BaseViewModel() {

    private val _signup: Channel<SignedUpUser> = Channel()
    val signup: Flow<SignedUpUser> = _signup.receiveAsFlow()

    fun doSignUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        passwordConfirmation: String,
        phone: String,
        address: String,
        fcmToken: String
    ) = signUpUseCase(
        SignUpUseCase.Params(
            SignUpRequest(
                firstName,
                lastName,
                email,
                password,
                passwordConfirmation,
                phone,
                address,
                fcmToken
            )
        ), viewModelScope, this
    )
    { it.fold(::handleFailure, ::handleResponse) }

    private fun handleResponse(data: SignedUpUser) {
        viewModelScope.launch { _signup.send(data) }
    }

    private val _login: Channel<NewUser> = Channel()
    val login: Flow<NewUser> = _login.receiveAsFlow()

    fun doLogin(email: String, password: String) =
        loginUseCase(LoginUseCase.Params(LoginRequest(email, password)), viewModelScope, this)
        { it.fold(::handleFailure, ::handleLoginResponse) }

    private fun handleLoginResponse(data: NewUser) {
        viewModelScope.launch { _login.send(data) }
    }

    private val _forgetPassword: Channel<ForgetPasswordResponse> = Channel()
    val forgetPassword: Flow<ForgetPasswordResponse> = _forgetPassword.receiveAsFlow()

    fun doForgetPassword(email: String) =
        forgetPasswordUseCase(ForgetPasswordUseCase.Params(ForgetPasswordRequest(email)), viewModelScope, this)
        { it.fold(::handleFailure, ::handleForgetPassword) }

    private fun handleForgetPassword(data: ForgetPasswordResponse) {
        viewModelScope.launch { _forgetPassword.send(data) }
    }

    private val _verifyOtp: Channel<ForgetPasswordResponse> = Channel()
    val verifyOtp: Flow<ForgetPasswordResponse> = _verifyOtp.receiveAsFlow()

    fun doVerifyOtp(otp: String) =
        verifyOtpUseCase(VerifyOtpUseCase.Params(VerifyOtpRequest( otp)), viewModelScope, this)
        { it.fold(::handleFailure, ::handleVerifyOtp) }

    private fun handleVerifyOtp(data: ForgetPasswordResponse) {
        viewModelScope.launch { _verifyOtp.send(data) }
    }

    private val _resetPassword: Channel<ForgetPasswordResponse> = Channel()
    val resetPassword: Flow<ForgetPasswordResponse> = _resetPassword.receiveAsFlow()

    fun doResetPassword(email: String, password: String, password_confirmation: String) =
        resetPasswordUseCase(ResetPasswordUseCase.Params(ResetPasswordRequest(email, password, password_confirmation)), viewModelScope, this)
        { it.fold(::handleFailure, ::handleResetPassword) }

    private fun handleResetPassword(data: ForgetPasswordResponse) {
        viewModelScope.launch { _resetPassword.send(data) }
    }

    private val _isBackPressedEnabled: Channel<Boolean> = Channel()
    val isBackPressedEnabled: Flow<Boolean> = _isBackPressedEnabled.receiveAsFlow()

    fun isBackPressedDispatcherEnabled(isEnabled: Boolean) {
        viewModelScope.launch { _isBackPressedEnabled.send(isEnabled) }
    }
}