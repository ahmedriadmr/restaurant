package com.doubleclick.rovleapp.dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.doubleclick.rovleapp.R

object DialogAddAddress {

    fun showTwoButtonPopup(
        context: Context,
        message: String,
        onOkClicked: (() -> Unit)? = null,
        onCancelClicked: (() -> Unit)? = null
    ) {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_add_address, null)
        builder.setView(view)
        val dialog = builder.create()

        // Set title and message
//        view.findViewById<TextView>(R.id.alertTitle).text = title
        view.findViewById<TextView>(R.id.alertMessage).text = message
        dialog.show()

        // Set positive button click
        view.findViewById<Button>(R.id.back).setOnClickListener {
            onOkClicked?.invoke()
            dialog.dismiss()
        }

        // Set negative button click
        view.findViewById<Button>(R.id.continuetopay).setOnClickListener {
            onCancelClicked?.invoke()
            dialog.dismiss()
        }


        // Set a white background color (you can change it to your desired color)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


    }
}