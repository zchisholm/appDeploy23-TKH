package com.example.autolistapp


data class ShoppingItem(
    var name: String,
    var quantity: Int,
    var isBought: Boolean = false
)