package com.doubleclick.rovleapp.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.databinding.LayoutCancelSubscriptionBinding

class DialogCancelSubscription (context: Context) : Dialog(context) {

    private lateinit var binding: LayoutCancelSubscriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutCancelSubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()

    }

    private fun setupClickListeners() {
        // TODO: Add any necessary click listeners here
        val volverButton: Button = findViewById(R.id.buttonCancel)
        volverButton.setOnClickListener {
            dismiss()
        }
        val imageView: ImageView = findViewById(R.id.imageView2)
        imageView.setOnClickListener {
            dismiss()
        }
        val continueButton: Button = findViewById(R.id.buttonContinue)
        continueButton.setOnClickListener {

        }
    }
}