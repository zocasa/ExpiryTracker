package com.example.expirytracker

import org.junit.Test

import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val currentDate = LocalDate.now()
        val expiryDate = LocalDate.of(2024, 1, 26)

        val daysBetween = ChronoUnit.DAYS.between(currentDate, expiryDate)

        val date = ExpiryDate(28, 1, 2024)
        getItemColor(date, 15, 30)
    }
}