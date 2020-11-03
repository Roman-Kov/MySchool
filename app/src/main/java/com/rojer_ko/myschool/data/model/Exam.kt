package com.rojer_ko.myschool.data.model

import com.rojer_ko.myschool.data.Subjects
import java.time.LocalDate

data class Exam(val subjects: Subjects,
                val dateOfStart: Long
)