package com.ganesh.twitterapp.di.component

import com.ganesh.twitterapp.MainActivity
import com.ganesh.twitterapp.di.module.AppModule
import com.ganesh.twitterapp.di.module.TrendsViewModelModule
import com.ganesh.twitterapp.data.repo.AppApiHelper
import com.ganesh.twitterapp.di.ActivityScope
import com.ganesh.twitterapp.di.module.ActivityModule
import com.ganesh.twitterapp.di.module.NetworkDIModule
import dagger.Component
import javax.inject.Singleton

@ActivityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ActivityModule::class,TrendsViewModelModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)
}