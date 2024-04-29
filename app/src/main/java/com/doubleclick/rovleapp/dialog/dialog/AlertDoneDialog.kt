package com.doubleclick.restaurant.utils.dialog

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.doubleclick.domain.ts.OnClickAlert

import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.databinding.LayoutDialogAlertDoneBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

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