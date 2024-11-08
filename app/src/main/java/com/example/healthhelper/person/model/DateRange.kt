package com.example.healthhelper.person.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class DateRange(
    val startDate: String = LocalDate.now().minusDays(30).format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
    val endDate: String = LocalDate.now().format( DateTimeFormatter.ofPattern("yyyy/MM/dd") )
)