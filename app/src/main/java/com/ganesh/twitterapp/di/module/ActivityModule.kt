package com.ganesh.twitterapp.di.module

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides


@Module
class ActivityModule constructor(var activity: Activity) {


    @Provides
    fun provideActivity(): Activity {
        return activity
    }


    @Provides
    fun getContxt(): Context {
        return activity.applicationContext
    }


}