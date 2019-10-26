package com.ganesh.twitterapp.di.component

import com.ganesh.twitterapp.di.module.ActivityModule
import com.ganesh.twitterapp.di.module.TrendsViewModelModule
import com.ganesh.twitterapp.di.module.NetworkDIModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkDIModule::class, TrendsViewModelModule::class, ActivityModule::class])
interface ActivityComponent {
}