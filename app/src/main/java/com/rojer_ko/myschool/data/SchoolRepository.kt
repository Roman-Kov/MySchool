package com.rojer_ko.myschool.data
import com.rojer_ko.myschool.data.model.Exam
import com.rojer_ko.myschool.data.model.Homework
import com.rojer_ko.myschool.data.model.SchoolClass
import java.time.LocalDate

/*Обработку данных оставил в репозитории так как объекты моковые и предполагается,
что с сервера приходят уже обработанные данные*/

class SchoolRepository: ISchoolRepository{

    //моковый список уроков, не отсортирован, с уроками на разные даты
    private val classesList = mutableListOf<SchoolClass>(
        SchoolClass(
            Subjects.Chemistry,
            LocalDate.parse("2020-10-28T09:00:00.000"),
            LocalDate.parse("2020-10-28T09:45:00.000"),
            false, false, "Mr. W White"
        ),
        SchoolClass(
            Subjects.English,
            LocalDate.parse("2020-10-28T10:00:00.000"),
            LocalDate.parse("2020-10-28T10:45:00.000"),
            false, false, "Mr. N Robinson"
        ),
        SchoolClass(
            Subjects.Informatics,
            LocalDate.parse("2020-10-28T12:00:00.000"),
            LocalDate.parse("2020-10-28T12:45:00.000"),
            true, false, "Mr. B Morgan"
        ),
        SchoolClass(
            Subjects.History,
            LocalDate.parse("2020-10-28T11:00:00.000"),
            LocalDate.parse("2020-10-28T11:45:00.000"),
            false, false, "Mr. G Kent",
            "Formation of the USA"
        ),
        SchoolClass(
            Subjects.Russian,
            LocalDate.parse("2020-10-29T10:00:00.000"),
            LocalDate.parse("2020-10-29T10:45:00.000"),
            true, false, "Mr. B Morgan"
        )
    )

    //моковый список ДЗ, не отсортирован, с ДЗ на разные даты, в том числе и на прошедшие
    private val homeworkList = mutableListOf<Homework>(
        Homework(Subjects.Literature, "Reed WAR AND PEACE", LocalDate.parse("2020-10-10")),
        Homework(Subjects.Literature, "Reed MR FROM SAN FRANSISCO", LocalDate.parse("2020-11-01")),
        Homework(Subjects.Mathematics, "Prove Fermat's theorem", LocalDate.parse("2020-11-03")),
        Homework(Subjects.Informatics, "Codding a program of school diary", LocalDate.parse("2020-11-02")),
        Homework(Subjects.Russian, "Learn rule ZHI-SHI ", LocalDate.parse("2020-11-02"))
    )

    //моковый список экзаменов, не отсортирован, экзамены на разные даты, в том числе и на прошедшие

    private val examsList = mutableListOf<Exam>(
        Exam(Subjects.Informatics, LocalDate.parse("2020-10-10T10:00:00.000")),
        Exam(Subjects.Informatics, LocalDate.parse("2020-11-03T10:00:00.000")),
        Exam(Subjects.Informatics, LocalDate.parse("2020-11-02T10:00:00.000")),
        Exam(Subjects.Informatics, LocalDate.parse("2020-11-10T10:00:00.000"))
    )

    override suspend fun getClassesForDate(date: LocalDate): List<SchoolClass> {
        val list = mutableListOf<SchoolClass>()

        for (item in classesList){
            if(item.dateTimeEnd.dayOfYear == date.dayOfYear){
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

    override suspend fun getNextExamDate(): LocalDate? {
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