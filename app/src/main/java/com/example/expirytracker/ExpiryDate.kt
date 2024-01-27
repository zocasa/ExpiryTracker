package com.example.expirytracker

import java.text.DateFormatSymbols
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class ExpiryDate(
    val date: Int,
    val month: Int,
    val year: Int,
    val displayableMonth: String = DateFormatSymbols().months[month-1]
): Comparable<ExpiryDate> {
    val localDate: LocalDate = LocalDate.of(year, month, date)

    override fun compareTo(other: ExpiryDate): Int {
        if (this === other)
            return 0

        val daysBetween = ChronoUnit.DAYS.between(this.localDate, other.localDate)

        return if (daysBetween < 0L)
            1
        else if (daysBetween == 0L)
            0
        else
            -1
    }
}
