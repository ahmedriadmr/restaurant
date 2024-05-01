package com.doubleclick.restaurant.core.extension

import android.text.InputFilter
import android.widget.EditText

fun EditText.setInputFilter(filter: InputFilter) {
    this.filters = arrayOf()
    this.filters = arrayOf(filter)
}