package com.rojer_ko.myschool.di

import com.rojer_ko.myschool.data.ISchoolRepository
import com.rojer_ko.myschool.data.SchoolRepository
import com.rojer_ko.myschool.presentation.viewmodel.ClassesScreenViewModel
import com.rojer_ko.myschool.presentation.viewmodel.HomeScreenViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{
    single<ISchoolRepository> { SchoolRepository()}
}
val mainModule = module{
    viewModel {HomeScreenViewModel(get())}
    viewModel {ClassesScreenViewModel(get())}
}