package com.rojer_ko.myschool.data

import com.rojer_ko.myschool.data.model.SchoolClass
import com.rojer_ko.myschool.data.model.Homework
import java.time.LocalDate

interface ISchoolRepository {

    fun getClassesForDate(date: LocalDate): List<SchoolClass>
    fun getHomework(): List<Homework>
    fun getNextExamDate(): LocalDate?
}