package com.doubleclick.restaurant.core.functional

import android.app.Dialog
import android.content.Context
import com.doubleclick.restaurant.R

object ProgressHandler {
    private var progressDialog: Dialog? = null

    fun handleProgress(isLoading: Boolean = false, context: Context) {
        when (isLoading) {
            true -> showProgress(context)
            false -> hideProgress()
        }
    }

    private fun showProgress(context: Context) {
        if (progressDialog?.isShowing == true) {
            hideProgress()
        }
        progressDialog = Dialog(context, R.style.TransparentDialog).apply {
            setContentView(R.layout.layout_loading)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            show()
        }
    }

    private fun hideProgress() {
        progressDialog?.dismiss()
    }
}