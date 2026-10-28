package com.example.myapplication.data.model

import java.util.UUID

data class InventoryItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val stock: Int
)
