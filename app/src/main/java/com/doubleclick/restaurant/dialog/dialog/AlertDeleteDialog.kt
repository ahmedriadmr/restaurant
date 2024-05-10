package com.doubleclick.restaurant.dialog.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.databinding.LayoutDialogAlertDeleteBinding

class AlertDeleteDialog(context: Context) : Dialog(context) {

    private lateinit var binding: LayoutDialogAlertDeleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutDialogAlertDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()

    }

    private fun setupClickListeners() {
        // TODO: Add any necessary click listeners here
        val removeButton: TextView = findViewById(R.id.remove)
        removeButton.setOnClickListener {
            dismiss()
        }

        val cancelButton: TextView = findViewById(R.id.cancel)
        cancelButton.setOnClickListener {
            dismiss()
        }
    }
}