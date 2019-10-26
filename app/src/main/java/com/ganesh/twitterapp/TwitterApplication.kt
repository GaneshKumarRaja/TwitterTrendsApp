package com.ganesh.twitterapp

import android.app.Application
import com.ganesh.twitterapp.di.component.AppComponent
import com.ganesh.twitterapp.di.component.DaggerAppComponent



class TwitterApplication : Application() {
    //

    override fun onCreate() {
        super.onCreate()
       // initComponet()
    }
    fun initComponet(): AppComponent {
        return DaggerAppComponent.builder().build()
    }

}