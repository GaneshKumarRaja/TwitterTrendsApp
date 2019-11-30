package com.ganesh.twitterapp.di.module

import android.app.Activity
import com.ganesh.twitterapp.data.persistence.KeyValueHandler
import com.ganesh.twitterapp.data.repo.APIHelper
import com.ganesh.twitterapp.di.ActivityScope
import com.ganesh.twitterapp.domain.TrendsInteractor
import com.ganesh.twitterapp.domain.TrendsUsecases
import com.ganesh.twitterapp.util.EnableGPS
import com.ganesh.twitterapp.util.GPSDialog
import dagger.Module
import dagger.Provides

@Module
class ActivityModule constructor(var activity: Activity) {

    @Provides
    @ActivityScope
    fun provideActivity(): Activity {
        return activity
    }


    @Provides
    @ActivityScope
    fun provideGps(activity: Activity): GPSDialog {
        return GPSDialog(activity)
    }

    @Provides
    @ActivityScope
    fun provideEnableGPS(activity: Activity, gpsDialog: GPSDialog): EnableGPS {
        return EnableGPS(activity, gpsDialog)
    }

    @Provides
    @ActivityScope
    fun providerTrendsInteractor(apiHelper: APIHelper, handler: KeyValueHandler): TrendsUsecases {
        return TrendsInteractor(apiHelper, handler)
    }


}