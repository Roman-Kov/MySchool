package com.rojer_ko.myschool.data.model

import com.rojer_ko.myschool.data.Subjects

data class SchoolClass(val subject: Subjects,
                       val dateTimeOfStart: Long,
                       val dateTimeEnd: Long,
                       val isExtra: Boolean,
                       val isOnline: Boolean,
                       val teachersName: String,
                       val description: String = "")