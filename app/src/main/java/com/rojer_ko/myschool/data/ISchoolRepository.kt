package com.rojer_ko.myschool.data

import com.rojer_ko.myschool.data.model.SchoolClass
import com.rojer_ko.myschool.data.model.Homework
import java.time.LocalDate

interface ISchoolRepository {

    suspend fun getClassesForDate(date: Long): List<SchoolClass>
    suspend fun getHomework(): List<Homework>
    suspend fun getNextExamDate(): Long?
}