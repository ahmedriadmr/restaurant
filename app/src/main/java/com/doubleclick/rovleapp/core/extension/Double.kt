package com.doubleclick.rovleapp.core.extension

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale


fun Double.round(): String = DecimalFormat("#.##", DecimalFormatSymbols(Locale.US)).format(this)

val Double.formatted: String
    get() {
        val stringValue = this.round()

        return if (stringValue.contains('.') && !stringValue.contains('e', ignoreCase = true)) {
            stringValue.replace("0*$".toRegex(), "").replace("\\.$".toRegex(), "")
        } else {
            stringValue
        }
    }