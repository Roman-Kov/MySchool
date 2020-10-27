package com.rojer_ko.myschool

import android.app.Application
import com.rojer_ko.myschool.di.appModule
import com.rojer_ko.myschool.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            appModule
            mainModule
        }
    }
}