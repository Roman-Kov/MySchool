package com.rojer_ko.myschool.data
import com.rojer_ko.myschool.data.model.Exam
import com.rojer_ko.myschool.data.model.Homework
import com.rojer_ko.myschool.data.model.SchoolClass
import java.text.SimpleDateFormat
import java.util.*

/*Обработку данных оставил в репозитории так как объекты моковые и предполагается,
что с сервера приходят уже обработанные данные*/

class SchoolRepository: ISchoolRepository{

    //моковый список уроков, не отсортирован, с уроками на разные даты
    private val classesList = mutableListOf<SchoolClass>(
        SchoolClass(
            Subjects.Chemistry,
            //2020-10-28T09:00:00
            1603875600000,
            //2020-10-28T09:45:00
            1603878300000,
            isExtra = false, isOnline = false, teachersName = "Mr. W White"
        ),
        SchoolClass(
            Subjects.English,
            //2020-10-28T10:00:00
            1603879200000,
            //2020-10-28T10:45:00
            1603881900000,
            isExtra = false, isOnline = false, teachersName = "Mr. N Robinson"
        ),
        SchoolClass(
            Subjects.Informatics,
            //2020-10-28T12:00:00
            1603886400000,
            //2020-10-28T12:45:00
            1603889100000,
            isExtra = true, isOnline = false, teachersName = "Mr. B Morgan"
        ),
        SchoolClass(
            Subjects.History,
            //2020-10-28T11:00:00
            1603885500000,
            //2020-10-28T11:45:00
            1603885500000,
            isExtra = false, isOnline = false, teachersName = "Mr. G Kent",
            description = "Formation of the USA"
        ),
        SchoolClass(
            Subjects.Russian,
            //2020-10-29T10:00:00
            1603965600000,
            //2020-10-29T10:45:00
            1603968300000,
            isExtra = true, isOnline = false, teachersName = "Mr. B Morgan"
        )
    )

    //моковый список ДЗ, не отсортирован, с ДЗ на разные даты, в том числе и на прошедшие
    private val homeworkList = mutableListOf(
        // 1602288000000 - 2020-10-10
        // 1604188800000 - 2020-11-01
        // 1604361600000 - 2020-11-03
        // 1604275200000 - 2020-11-02

        Homework(Subjects.Literature, "Reed WAR AND PEACE", 1602288000000),
        Homework(Subjects.Literature, "Reed MR FROM SAN FRANSISCO", 1604188800000),
        Homework(Subjects.Mathematics, "Prove Fermat's theorem", 1604361600000),
        Homework(Subjects.Informatics, "Codding a program of school diary", 1604275200000),
        Homework(Subjects.Russian, "Learn rule ZHI-SHI ", 1604275200000)
    )

    //моковый список экзаменов, не отсортирован, экзамены на разные даты, в том числе и на прошедшие
    //1602324000000 - 2020-10-10T10:00:00
    //1604397600000 - 2020-11-03T10:00:00
    //1604052000000 - 2020-10-30T10:00:00
    //1605002400000 - 2020-11-10T10:00:00

    private val examsList = mutableListOf(
        Exam(Subjects.Informatics, 1602324000000),
        Exam(Subjects.Informatics, 1604397600000),
        Exam(Subjects.Informatics, 1604052000000),
        Exam(Subjects.Informatics, 1605002400000)
    )

    override suspend fun getClassesForDate(date: Long): List<SchoolClass> {
        val list = mutableListOf<SchoolClass>()
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        for (item in classesList){
            if(dateFormat.format(item.dateTimeEnd) == dateFormat.format(date)){
                list.add(item)
            }
        }
        list.sortBy { it.dateTimeOfStart }
        return list
    }

    override suspend fun getHomework(): List<Homework> {
        val list = mutableListOf<Homework>()

        for(item in homeworkList){
            if(item.deadLine >= currentDateTime){
                list.add(item)
            }
        }
        list.sortBy { it.deadLine }
        return list
    }

    override suspend fun getNextExamDate(): Long? {
        var currentItem: Exam? = null

        for(item in examsList){
            if(item.dateOfStart >= currentDateTime){
                currentItem = item
                break
            }
        }

        if(currentItem != null){
            for (item in examsList){
                currentItem?.let {
                    if(item.dateOfStart >= currentDateTime && item.dateOfStart < it.dateOfStart){
                        currentItem = item
                    }
                }
            }
            currentItem?.let {
                return it.dateOfStart
            }
        }
        return null
    }
}