package com.ganesh.twitterapp.di.component

import com.ganesh.twitterapp.view.MainActivity
import com.ganesh.twitterapp.di.module.TrendsViewModelModule
import com.ganesh.twitterapp.di.ActivityScope
import com.ganesh.twitterapp.di.module.ActivityModule
import dagger.Component

@ActivityScope
@Component(

    dependencies = [AppComponent::class],
    modules = [ActivityModule::class,TrendsViewModelModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)
}