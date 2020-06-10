package com.swpu.ylq.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication :Application() {
    companion object {
        const val TOKEN = "sPIyUByOdqxUaPyV"
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }
}