package com.ganesh.twitterapp.di.component

import android.content.Context
import com.ganesh.twitterapp.data.repo.APIHelper
import com.ganesh.twitterapp.di.module.AppModule
import com.ganesh.twitterapp.di.module.NetworkDIModule
import com.ganesh.twitterapp.util.KeyValueHandler
import com.google.android.gms.location.LocationRequest
import dagger.Component
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkDIModule::class, AppModule::class])
interface AppComponent {



    fun shareProv(): ReactiveLocationProvider

    fun shareLocationRequest(): LocationRequest

    fun shareHelper(): APIHelper

    fun shareContext(): Context

    fun sharePreference():KeyValueHandler


}