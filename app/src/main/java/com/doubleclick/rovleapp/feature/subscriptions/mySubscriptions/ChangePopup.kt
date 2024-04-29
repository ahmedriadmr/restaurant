package com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.RadioGroup
import com.doubleclick.rovleapp.R

object ChangePopup {

    interface OnSelectionChangeListener {
        fun onSelectionChanged(selectedOption: String)
    }

    interface OnContinueClickListener {
        fun onContinueClicked()
    }

    fun showPopup(
        context: Context,
        listener: OnSelectionChangeListener,
        continueListener: OnContinueClickListener
    ) {
        // Inflate the popup layout
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.custom_alert_dialog_change, null)
        builder.setView(view)
        val dialog = builder.create()

        // Find views in the popup layout
        val radioGroup: RadioGroup = view.findViewById(R.id.radioGroup)
        val buttonCancel: Button = view.findViewById(R.id.buttonCancel)
        val buttonContinue: Button = view.findViewById(R.id.buttonContinue)

        // Set radio button click listener
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedOption: String = when (checkedId) {
                R.id.radioButton1 -> "Same Roaster"
                R.id.radioButton2 -> "Another Roaster"
                else -> ""
            }
            listener.onSelectionChanged(selectedOption)
        }

        // Set button click listeners
        buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        buttonContinue.setOnClickListener {
            // Handle continue button click if needed
            continueListener.onContinueClicked()
            dialog.dismiss()
        }

        dialog.show()

        // Set a white background color (you can change it to your desired color)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
