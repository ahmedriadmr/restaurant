package com.doubleclick.restaurant.dialog.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.databinding.LayoutDialogAlertDoneBinding

class AlertDoneDialog(context: Context) : Dialog(context) {

    private lateinit var binding: LayoutDialogAlertDoneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutDialogAlertDoneBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()

    }

    private fun setupClickListeners() {
        // TODO: Add any necessary click listeners here
        val yourOrder: TextView = findViewById(R.id.your_order)
        yourOrder.setOnClickListener {
            dismiss()
        }

        val home: TextView = findViewById(R.id.home)
        home.setOnClickListener {
            dismiss()
        }
    }
}