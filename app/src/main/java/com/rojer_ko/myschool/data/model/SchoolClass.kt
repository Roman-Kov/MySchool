package com.rojer_ko.myschool.data.model

import com.rojer_ko.myschool.data.Subjects
import java.time.LocalDate

data class SchoolClass(val subjects: Subjects,
                       val dateTimeOfStart: LocalDate,
                       val dateTimeEnd: LocalDate,
                       val isExtra: Boolean,
                       val isOnline: Boolean,
                       val teachersName: String,
                       val description: String = "")