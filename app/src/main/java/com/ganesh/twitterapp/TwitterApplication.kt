package com.ganesh.twitterapp

import android.app.Application
import com.ganesh.twitterapp.di.component.AppComponent
import com.ganesh.twitterapp.di.component.DaggerAppComponent
import com.ganesh.twitterapp.di.module.AppModule
import com.ganesh.twitterapp.di.module.NetworkDIModule


class TwitterApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    fun initDagger() {
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

}