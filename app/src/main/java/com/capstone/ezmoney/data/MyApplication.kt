package com.capstone.ezmoney.data

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    // Akses konteks aplikasi global
    companion object {
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext  // Set konteks aplikasi global
    }
}

