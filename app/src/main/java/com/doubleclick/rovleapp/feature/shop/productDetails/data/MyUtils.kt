package com.doubleclick.rovleapp.feature.shop.productDetails.data

import android.app.AlertDialog
import android.content.Context

object MyUtils {

    fun showTwoButtonPopup(
        context: Context,
        title: String,
        message: String,
        onOkClicked: () -> Unit,
        onCancelClicked: () -> Unit
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("PAY NOW") { _, _ ->
                // Custom action when "OK" is clicked
                onOkClicked.invoke()
            }
            .setNegativeButton("BACK TO SHOP") { _, _ ->
                // Custom action when "Cancel" is clicked
                onCancelClicked.invoke()
            }

        val dialog = builder.create()
        dialog.show()
    }

}