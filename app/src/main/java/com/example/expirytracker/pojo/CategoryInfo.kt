package com.example.expirytracker.pojo

data class CategoryInfo(
    val name: String,
    val minDays: Long = Long.MIN_VALUE,
    val maxDays: Long = Long.MAX_VALUE
)
