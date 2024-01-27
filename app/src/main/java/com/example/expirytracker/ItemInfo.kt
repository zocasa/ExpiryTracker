package com.example.expirytracker

data class ItemInfo(
    val name: String,
    val expiryDate: ExpiryDate,
    val productLink: String? = null
) {
    var daysRemaining: Long = 0L
}
