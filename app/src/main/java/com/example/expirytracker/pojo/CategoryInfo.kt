package com.example.expirytracker.pojo

data class CategoryInfo(
    val name: String,
    val min: Long = Long.MIN_VALUE,
    val max: Long = Long.MAX_VALUE
)
