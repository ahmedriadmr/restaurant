package com.doubleclick.restaurant.feature.subscriptions.mySubscriptions
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.views.customSpinner.CustomSpinner

object PausePopup {

    private fun setupPausePeriodSpinner(view: View, context: Context): Spinner {
        val spinner = view.findViewById<CustomSpinner>(R.id.pause_period_spinner) // Use CustomSpinner instead of Spinner
        val adapter = ArrayAdapter.createFromResource(
            context,
            R.array.pause_periods,
            R.layout.text_spinner_item // Use the custom layout for spinner items
        )
        adapter.setDropDownViewResource(R.layout.custom_spinner)
        spinner.adapter = adapter
        return spinner
    }


    fun showTwoButtonPopup(
        context: Context,
        title: String,
        onOkClicked: ((String) -> Unit)? = null,
        onCancelClicked: (() -> Unit)? = null
    ) {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.alert_dialog_pause, null)
        builder.setView(view)
        val dialog = builder.create()

        val pausePeriodSpinner = setupPausePeriodSpinner(view, context)

        // Set title and message
        view.findViewById<TextView>(R.id.alertTitle).text = title
        dialog.show()

        // Set positive button click
        view.findViewById<Button>(R.id.btnSave).setOnClickListener {
            val selectedPeriod = pausePeriodSpinner.selectedItem.toString()
            onOkClicked?.invoke(selectedPeriod)
            dialog.dismiss()
        }

        // Set negative button click
        view.findViewById<Button>(R.id.btncancel).setOnClickListener {
            onCancelClicked?.invoke()
            dialog.dismiss()
        }
        view.findViewById<ImageView>(R.id.arrow_spinner).setOnClickListener {
            view.findViewById<CustomSpinner>(R.id.pause_period_spinner).performClick()
        }

        // Set a transparent background color
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}