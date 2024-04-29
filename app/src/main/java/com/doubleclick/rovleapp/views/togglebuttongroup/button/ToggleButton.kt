package com.doubleclick.rovleapp.views.togglebuttongroup.button

import android.widget.Checkable

/**
 * Created By Eslam Ghazy on 11/20/2022
 */
interface ToggleButton : Checkable {
    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener?)
}
