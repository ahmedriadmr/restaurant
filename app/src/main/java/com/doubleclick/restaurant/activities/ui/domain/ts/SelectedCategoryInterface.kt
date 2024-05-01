package com.doubleclick.domain.ts

import com.doubleclick.domain.model.category.get.Category


interface SelectedCategoryInterface {
    fun selceted(category: Category)
    fun unselceted(category: Category)
}