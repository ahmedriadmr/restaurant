package com.doubleclick.restaurant.views.customSpinner

import android.content.Context
import android.content.res.Resources.Theme
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSpinner

class CustomSpinner : AppCompatSpinner {

    constructor(context: Context?) : super(context!!)
    constructor(context: Context, mode: Int) : super(context, mode)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, mode: Int) : super(
        context,
        attrs,
        defStyleAttr,
        mode
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        mode: Int,
        popupTheme: Theme?
    ) : super(context, attrs, defStyleAttr, mode, popupTheme)




    /**
     * Register the listener which will listen for events.
     */
    fun setSpinnerEventsListener() {
//        mListener = onSpinnerEventsListener
    }

    /* fun <T> setSelection(item: T): Any? {
         return this.selectedItem
     }*/
}
