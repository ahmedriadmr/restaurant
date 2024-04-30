package com.doubleclick.restaurant.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.databinding.LayoutAlertBinding

class DialogAlert (context: Context) : Dialog(context) {

    private lateinit var binding: LayoutAlertBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()

    }

    private fun setupClickListeners() {
        // TODO: Add any necessary click listeners here
        val closeButton: ImageView = findViewById(R.id.imageClose)
        closeButton.setOnClickListener {
            dismiss()
        }
    }
}