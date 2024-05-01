package com.doubleclick.domain.ts

import com.doubleclick.domain.model.items.get.Size

interface OnSizeSelected {
    fun sizeSelected(size: Size)
}