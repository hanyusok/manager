package com.example.manager

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Manager : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}