package com.example.autolistapp


data class ShoppingItem(
    var name: String,
    var quantity: Int? = null,
    var isBought: Boolean = false,
    var isCrossedOut: Boolean = false
)