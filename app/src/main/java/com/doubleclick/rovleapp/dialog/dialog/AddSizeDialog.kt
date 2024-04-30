package com.doubleclick.restaurant.utils.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.doubleclick.domain.model.sizes.Sizes
import com.doubleclick.domain.ts.OnAddItem


import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.databinding.LayoutAddItemSizeBinding
import com.doubleclick.restaurant.swipetoactionlayout.utils.SimpleTextWatcher
import com.doubleclick.restaurant.utils.isNotNullOrEmptyString

class AddSizeDialog(context: Context, private val onAddItem: OnAddItem) :
    Dialog(context, R.style.CustomAlertDialog) {

    private lateinit var binding: LayoutAddItemSizeBinding
    private var name = ""
    private var price = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutAddItemSizeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.add.setOnClickListener {
            if (name.isNotNullOrEmptyString() && price.isNotNullOrEmptyString())
                onAddItem.onClickAdd(Sizes(name, price.toDouble()))
            dismiss()
        }
        binding.sizeName.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
                super.onTextChanged(char, p1, p2, p3)
                name = char.toString()
            }
        })
        binding.sizePrice.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
                super.onTextChanged(char, p1, p2, p3)
                price = char.toString()
            }
        })

    }

}