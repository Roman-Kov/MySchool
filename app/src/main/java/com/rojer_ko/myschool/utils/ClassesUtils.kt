package com.rojer_ko.myschool.utils

import com.rojer_ko.myschool.data.model.SchoolClass

fun getCurrentSchoolClass(data: List<SchoolClass>, currentTime: Long): Int{

    for(item in data){
        val timeRange = item.dateTimeOfStart .. item.dateTimeEnd
        if(currentTime in timeRange) return data.indexOf(item)
    }

    return 0
}