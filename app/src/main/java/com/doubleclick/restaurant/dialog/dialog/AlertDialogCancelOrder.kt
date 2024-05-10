package com.doubleclick.restaurant.dialog.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.databinding.AlertDialogCancelOrderBinding

class AlertDialogCancelOrder (context: Context) : Dialog(context) {

    private lateinit var binding: AlertDialogCancelOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AlertDialogCancelOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()

    }

    private fun setupClickListeners() {
        // TODO: Add any necessary click listeners here
        val yesButton: TextView = findViewById(R.id.cancel)
        yesButton.setOnClickListener {
            dismiss()
        }

        val noButton: TextView = findViewById(R.id.dismiss)
        noButton.setOnClickListener {
            dismiss()
        }
    }
}