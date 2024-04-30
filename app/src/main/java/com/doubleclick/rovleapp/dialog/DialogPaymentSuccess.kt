package com.doubleclick.restaurant.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.databinding.DialogPaymentSuccessBinding

class DialogPaymentSuccess (context: Context) : Dialog(context) {

    private lateinit var binding: DialogPaymentSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogPaymentSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        // TODO: Add any necessary click listeners here
        val terminarButton: Button = findViewById(R.id.terminar)
        terminarButton.setOnClickListener {
            dismiss()
        }
    }
}