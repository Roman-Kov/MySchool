package com.rojer_ko.myschool.data.model

import com.rojer_ko.myschool.data.Subjects

data class Homework(val subject: Subjects,
                    val description: String,
                    val deadLine: Long
)