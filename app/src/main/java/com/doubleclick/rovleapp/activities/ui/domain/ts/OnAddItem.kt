package com.doubleclick.domain.ts

import com.doubleclick.domain.model.sizes.Sizes

interface OnAddItem {
    fun onClickAdd(sizes: Sizes)
}