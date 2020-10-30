package com.rojer_ko.myschool.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rojer_ko.myschool.data.ISchoolRepository
import com.rojer_ko.myschool.data.model.Homework
import com.rojer_ko.myschool.data.model.SchoolClass
import kotlinx.coroutines.*

class HomeScreenViewModel(private val repository: ISchoolRepository): ViewModel() {

    private val _nextExamLiveData = MutableLiveData<Long>()
    val nextExamLiveData: LiveData<Long> = _nextExamLiveData

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

    fun getNextExamDate(){
        nextExamCoroutineScope.launch {
            _nextExamLiveData.postValue(repository.getNextExamDate())
        }
    }

    override fun onCleared() {
        super.onCleared()

        nextExamCoroutineScope.coroutineContext.cancelChildren()
        classesCoroutineScope.coroutineContext.cancelChildren()
        homeWorksCoroutineScope.coroutineContext.cancelChildren()
    }
}