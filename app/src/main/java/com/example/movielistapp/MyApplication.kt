package com.example.movielistapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        myApplication = this



    }



    companion object {
        // private const val TAG = 99999
        lateinit var myApplication: MyApplication

    }


}