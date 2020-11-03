package com.rojer_ko.myschool.domain

data class NextExamTimerSeparated(
    val firstHour: String,
    val secondHour: String,
    val firstMinute: String,
    val secondMinute: String,
    val firstSecond: String,
    val secondSecond: String
)