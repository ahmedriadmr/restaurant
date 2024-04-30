package com.doubleclick.restaurant.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.databinding.DialogForgetPasswordBinding

class DialogForgetPassword(context: Context) : Dialog(context) {

    private lateinit var binding: DialogForgetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        // TODO: Add any necessary click listeners here
        val terminarButton: TextView = findViewById(R.id.terminar)
        terminarButton.setOnClickListener {
            dismiss()

        }
    }
}