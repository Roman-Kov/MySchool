package com.rojer_ko.myschool.presentation.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.rojer_ko.myschool.data.ISchoolRepository
import com.rojer_ko.myschool.data.currentDateTime
import com.rojer_ko.myschool.data.model.Homework
import com.rojer_ko.myschool.data.model.SchoolClass
import com.rojer_ko.myschool.domain.NextExamTimerSeparated
import kotlinx.coroutines.*

class HomeScreenViewModel(private val repository: ISchoolRepository): ViewModel() {

    private var timerIsStarted = false

    private val nextExamObserver = Observer<Long?>{
        startExamTimer(it)
    }

    private val _nextExamLiveData = MutableLiveData<Long?>()

    private val _nextExamTimerLiveData = MutableLiveData<NextExamTimerSeparated>()
    val nextExamTimerLiveDataLiveData: LiveData<NextExamTimerSeparated> = _nextExamTimerLiveData

    private val _classesLiveData = MutableLiveData<List<SchoolClass>>()
    val classesLiveData: LiveData<List<SchoolClass>> = _classesLiveData

    private val _homeWorksLiveData = MutableLiveData<List<Homework>>()
    val homeWorksLiveData: LiveData<List<Homework>> = _homeWorksLiveData

    private val nextExamCoroutineScope = CoroutineScope(
        Dispatchers.IO
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleNextExamError(throwable)
        })

    private val classesCoroutineScope = CoroutineScope(
        Dispatchers.IO
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleClassesError(throwable)
        })

    private val homeWorksCoroutineScope = CoroutineScope(
        Dispatchers.IO
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleHomeWorksError(throwable)
        })

    private fun handleNextExamError(throwable: Throwable){
        Log.e("Error", throwable.message?:"NextExam error")
    }

    private fun handleClassesError(throwable: Throwable){
        Log.e("Error", throwable.message?:"Classes error")
    }

    private fun handleHomeWorksError(throwable: Throwable){
        Log.e("Error", throwable.message?:"HomeWork error")
    }

    fun getClassesForDate(date: Long){
        classesCoroutineScope.launch {
            _classesLiveData.postValue(repository.getClassesForDate(date))
        }
    }

    fun getHomework(){
        homeWorksCoroutineScope.launch {
            _homeWorksLiveData.postValue(repository.getHomework())
        }
    }

   private fun getNextExamDate(){
        nextExamCoroutineScope.launch {
            _nextExamLiveData.postValue(repository.getNextExamDate())
        }
    }

    fun getExamTimer(){

        if(!timerIsStarted){
            getNextExamDate()
            _nextExamLiveData.observeForever(nextExamObserver)
        }
        timerIsStarted = true
    }

    private fun startExamTimer(nextExamDate: Long?){
        nextExamDate?.let {
            val nextExamTimer = it - currentDateTime
            val timer = object: CountDownTimer(nextExamTimer, 1000){
                override fun onTick(p0: Long) {
                    _nextExamTimerLiveData.value = separateTimer(p0)
                }

                override fun onFinish() {
                    getNextExamDate()
                }
            }
            timer.start()
        }
    }

    private fun separateTimer(timer: Long):NextExamTimerSeparated{
        val hours = timer/3600000
        val hoursRemainder = timer%3600000
        val minutes = hoursRemainder/60000
        val minutesRemainder = hoursRemainder%60000
        val seconds = minutesRemainder/1000

        val firstHour = hours / 10
        val secondHour = hours - firstHour*10
        val firstMinute = minutes / 10
        val secondMinute = minutes - firstMinute*10
        val firstSecond = seconds / 10
        val secondSecond = seconds - firstSecond*10

        return NextExamTimerSeparated(
            firstHour.toString(),
            secondHour.toString(),
            firstMinute.toString(),
            secondMinute.toString(),
            firstSecond.toString(),
            secondSecond.toString()
        )

    }

    override fun onCleared() {
        super.onCleared()

        nextExamCoroutineScope.coroutineContext.cancelChildren()
        classesCoroutineScope.coroutineContext.cancelChildren()
        homeWorksCoroutineScope.coroutineContext.cancelChildren()
        _nextExamLiveData.removeObserver(nextExamObserver)
    }
}