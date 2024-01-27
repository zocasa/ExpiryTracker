package com.example.expirytracker

import java.util.TreeSet

data class ItemInfo(
    val name: String,
    val expiryDates: TreeSet<ExpiryDate>,
    val productLink: String? = null
): Comparable<ItemInfo> {
    val expiryDate: ExpiryDate
        get() {
            return expiryDates.first()
        }
    var daysRemaining: Long = 0L

    override fun compareTo(other: ItemInfo): Int {
        if (this === other)
            return 0

        val d = this.expiryDate.compareTo(other.expiryDate)

        if (d != 0)
            return d

        return this.name.compareTo(other.name)
    }
}
