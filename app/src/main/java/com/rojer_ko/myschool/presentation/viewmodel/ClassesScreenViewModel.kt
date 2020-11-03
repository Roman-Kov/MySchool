package com.rojer_ko.myschool.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rojer_ko.myschool.data.ISchoolRepository
import com.rojer_ko.myschool.data.model.Homework
import com.rojer_ko.myschool.data.model.SchoolClass
import kotlinx.coroutines.*
import java.time.LocalDate

class ClassesScreenViewModel(private val repository: ISchoolRepository): ViewModel() {

    private val _classesLiveData = MutableLiveData<List<SchoolClass>>()
    val classesLiveData: LiveData<List<SchoolClass>> = _classesLiveData


    private val classesCoroutineScope = CoroutineScope(
        Dispatchers.IO
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleClassesError(throwable)
        })

    private fun handleClassesError(throwable: Throwable){
        Log.e("Error", throwable.message?:"Classes error")
    }

    fun getClassesForDate(date: Long){
        classesCoroutineScope.launch {
            _classesLiveData.postValue(repository.getClassesForDate(date))
        }
    }

    override fun onCleared() {
        super.onCleared()

        classesCoroutineScope.coroutineContext.cancelChildren()
    }
}