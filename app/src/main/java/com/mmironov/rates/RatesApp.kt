package com.mmironov.rates

import android.app.Application
import android.content.Context

class RatesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        lateinit var context: Context
    }
}