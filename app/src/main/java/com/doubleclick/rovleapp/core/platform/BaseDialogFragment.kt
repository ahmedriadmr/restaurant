package com.doubleclick.rovleapp.core.platform

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.exception.Failure
import com.doubleclick.rovleapp.core.extension.observeOrNull
import com.doubleclick.rovleapp.core.platform.local.AppSettingsSource
import com.doubleclick.rovleapp.core.platform.local.UserAccess
import javax.inject.Inject


abstract class BaseDialogFragment(layoutId: Int) : androidx.fragment.app.DialogFragment(layoutId) {
    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var appSettingsSource: AppSettingsSource
    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeOrNull(appSettingsSource.user(), ::renderAuthenticating)
    }

    open fun renderAuthenticating(user: UserAccess?) {}


    fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> renderFailure("please check network")
            is Failure.FeatureFailure -> renderFeatureFailure(failure.message)
            is Failure.UnExpectedError -> renderFailure(failure.message)
            is Failure.Authentication -> Unit
            else -> renderFailure("something went wrong")
        }
    }

    open fun renderFailure(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    open fun renderFeatureFailure(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}