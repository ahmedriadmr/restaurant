package com.doubleclick.restaurant.utils.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.doubleclick.domain.ts.OnClickAlert

import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.databinding.LayoutDialogAlertDoneBinding

class AlertDoneDialog(context: Context, val onClickAlert: OnClickAlert) :
    Dialog(context, R.style.CustomAlertDialog) {

    private lateinit var binding: LayoutDialogAlertDoneBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutDialogAlertDoneBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.cancel.setOnClickListener {
            onClickAlert.onClickOk()
            dismiss()
        }


    }


}