package com.doubleclick.customspinner

import android.widget.Spinner

interface OnSpinnerEventsListener {
    fun onPopupWindowOpened(spinner: Spinner?)
    fun onPopupWindowClosed(spinner: Spinner?)
}