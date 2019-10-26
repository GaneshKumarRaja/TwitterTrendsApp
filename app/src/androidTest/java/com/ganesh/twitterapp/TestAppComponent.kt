package com.ganesh.twitterapp

import com.ganesh.twitterapp.di.module.ActivityModule
import com.ganesh.twitterapp.di.module.AppModule
import com.ganesh.twitterapp.di.module.TrendsViewModelModule
import com.ganesh.twitterapp.di.module.NetworkDIModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkDIModule::class, AppModule::class, ActivityModule::class, TrendsViewModelModule::class])
interface TestAppComponent {

    // fun inject(activity: MainActivity)
    //fun inject (apiHelper: AppApiHelper)

    fun inject(txt: ExampleInstrumentedTest)


}