package com.doubleclick.rovleapp.core.platform

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.doubleclick.rovleapp.R

object AuthPopup {
    private var dialog: AlertDialog? = null
    private var isShowing: Boolean = false
    fun showTwoButtonPopup(
        context: Context,
        title: String,
        message: String,
        onOkClicked: (() -> Unit)? = null,
        onCancelClicked: (() -> Unit)? = null
    ) {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.custom_alert_dialog_auth, null)
        builder.setView(view)
        dialog = builder.create()
        isShowing = true
        // Set title and message
        view.findViewById<TextView>(R.id.alertTitleAuth).text = title
        view.findViewById<TextView>(R.id.alertMessageAuth).text = message


        // Set positive button click
        view.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            onCancelClicked?.invoke()
            dismiss()
        }

        // Set negative button click
        view.findViewById<Button>(R.id.btnOk).setOnClickListener {
            onOkClicked?.invoke()
            dismiss()
        }

        // Set a white background color (you can change it to your desired color)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)
        dialog?.show()
    }

    // Add a dismiss method with a Context parameter
    fun dismiss() {
        dialog?.dismiss()
        dialog = null // Reset dialog reference to null after dismissal
        isShowing = false
    }

}