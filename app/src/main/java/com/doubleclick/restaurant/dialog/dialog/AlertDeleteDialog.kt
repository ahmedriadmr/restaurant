package com.doubleclick.restaurant.dialog.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.doubleclick.domain.model.carts.get.CartsModel
import com.doubleclick.domain.ts.OnClickAlert
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.databinding.LayoutDialogAlertDeleteBinding
import com.doubleclick.restaurant.feature.home.data.listCart.CartData


class AlertDeleteDialog(context: Context, val cartModel: CartData, val onClickAlert: OnClickAlert) :
    Dialog(context, R.style.CustomAlertDialog) {

    private lateinit var binding: LayoutDialogAlertDeleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutDialogAlertDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.dismiss.setOnClickListener {
            onClickAlert.dismiss()
            dismiss()
        }
        binding.remove.setOnClickListener {
            onClickAlert.onClickOk(cartModel)
            dismiss()
        }

    }


}