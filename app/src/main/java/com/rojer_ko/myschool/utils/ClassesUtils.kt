package com.rojer_ko.myschool.utils

import android.view.View
import com.rojer_ko.myschool.data.model.SchoolClass
import com.rojer_ko.myschool.presentation.ui.ClassesContainer
import java.text.SimpleDateFormat
import java.util.*

fun getCurrentSchoolClass(data: List<SchoolClass>, currentTime: Long): Int{

    for(item in data){
        var timeRange: LongRange
        timeRange = if(data.indexOf(item) == 0){
            item.dateTimeOfStart .. item.dateTimeEnd
        }else{
            val prevItem = data[data.indexOf(item) - 1]
            prevItem.dateTimeEnd .. item.dateTimeEnd
        }
        if(currentTime in timeRange) return data.indexOf(item)
    }

    return 0
}

fun convertSchoolClassesToClassesContainer(data: List<SchoolClass>, onClick: View.OnClickListener): List<ClassesContainer>{

    val classesContainer = mutableListOf<ClassesContainer>()

    for (item in data){
        item.apply {
            classesContainer.add(
                ClassesContainer(
                    classesItemTime = convertStartEndTimesToLine(dateTimeOfStart, dateTimeEnd),
                    classesItemDescription = description,
                    classesItemSubject = subject.title,
                    classesItemTeacher = teachersName,
                    onClick = onClick,
                    isOnline = isOnline,
                    isExtra = isExtra
                )
            )
        }
    }

    return classesContainer
}

fun convertStartEndTimesToLine(startTime: Long, endTime: Long):String{

    val dateFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
    val classStart = dateFormat.format(startTime)
    val classEnd = dateFormat.format(endTime)
    return "$classStart - $classEnd"
}