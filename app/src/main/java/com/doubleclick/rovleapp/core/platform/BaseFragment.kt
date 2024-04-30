package com.doubleclick.restaurant.core.platform

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.exception.Failure
import com.doubleclick.restaurant.core.extension.observeOrNull
import com.doubleclick.restaurant.core.platform.local.AppSettingsSource
import com.doubleclick.restaurant.core.platform.local.UserAccess
import javax.inject.Inject


abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {
    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var appSettingsSource: AppSettingsSource


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeOrNull(appSettingsSource.user(), ::renderAuthenticating)
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        when {
            enter -> {
                // Load your custom animation style
                val animation: Animation = AnimationUtils.loadAnimation(activity, android.R.anim.fade_in)

                // Apply the animation style to the window
                if (activity != null) {
                    activity?.window?.setWindowAnimations(R.style.Animation_WindowSlideUpDown)
                }
                return animation
            }

            !enter -> {
                val animation: Animation = AnimationUtils.loadAnimation(activity, android.R.anim.fade_out)

                // Apply the animation style to the window
                if (activity != null) {
                    activity?.window?.setWindowAnimations(R.style.Animation_WindowSlideUpDown)
                }
                return animation
            }
        }

        // If it's not an enter animation, use the default behavior
        return super.onCreateAnimation(transit, enter, nextAnim)
    }

    fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> renderFailure("please check network")
            is Failure.FeatureFailure -> renderFeatureFailure(failure.message)
            is Failure.UnExpectedError -> renderFailure(failure.message)
            is Failure.Authentication -> Unit
            else -> renderFailure("something went wrong")
        }
    }

    open fun renderAuthenticating(user: UserAccess?) {}

    open fun renderFailure(message: String?) {
        showToast(message)
    }

    open fun renderFeatureFailure(message: String?) {
        showToast(message)
    }

    private fun showToast(message: String?) {
        message?.let { Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }
    }
}