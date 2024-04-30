package com.doubleclick.restaurant.feature.shop.searchProduct.presentation

import android.text.InputFilter
import android.text.Spanned
import com.google.android.material.textfield.TextInputLayout

class RangeInputFilter(
    private val textInputLayout: TextInputLayout,
    private val min: Float,
    private val max: Float,
    private var showDisclaimer: Boolean,
    private val disclaimer: String? = null,
) : InputFilter {
    override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
        try {
            var value = dest.toString()

            value = if (source != null) {
                value.substring(0, dstart) + source.subSequence(start, end) + value.substring(dend, value.length)
            } else {
                value.substring(0, dstart) + value.substring(dend, value.length)
            }
            if (value.toFloat() in min..max) {
                textInputLayout.error = null
                return null // Accept the input
            }
            // value now contains the exact current value of the EditText
            return setError(showDisclaimer, disclaimer)
        } catch (nfe: NumberFormatException) {
            return setError(showDisclaimer, disclaimer)
        }
    }


    private fun setError(showDisclaimer: Boolean, disclaimer: String?): String? {
        return when (showDisclaimer) {
            true -> {
                textInputLayout.error = disclaimer
                null
            }

            false -> {
                ""
            }
        }
    }
}