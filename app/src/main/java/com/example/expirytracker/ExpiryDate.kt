package com.example.expirytracker

import java.text.DateFormatSymbols
import java.time.LocalDate

data class ExpiryDate(
    val date: Int,
    val month: Int,
    val year: Int,
    val displayableMonth: String = DateFormatSymbols().months[month-1]
) {
    val localDate: LocalDate = LocalDate.of(year, month, date)
}
