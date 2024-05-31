package com.doubleclick.restaurant.dialog.dialog

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.doubleclick.restaurant.R

class AlertDialogReceiveOrderDone : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_alert_dialog_receive_order_done)

        val id = intent.getStringExtra("FCM_MESSAGE_TEST")
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = "Alarm! Delivery of the order to\n No.table: $id"

        val acceptButton = findViewById<TextView>(R.id.your_order)
        acceptButton.setOnClickListener {
            finish()
        }

        val ignoreButton = findViewById<TextView>(R.id.home)
        ignoreButton.setOnClickListener {
            finish()
        }
    }
}