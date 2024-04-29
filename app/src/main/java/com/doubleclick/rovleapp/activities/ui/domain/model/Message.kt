package com.doubleclick.domain.model

import com.doubleclick.domain.model.auth.Code
import com.doubleclick.domain.model.items.get.Item

data class Message(
    val message: String?,
    val status: String?,
    val item: Item?,
    val code: Code?
)

