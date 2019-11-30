package com.ganesh.twitterapp.di.module

import android.app.Application
import android.content.Context
import com.ganesh.twitterapp.util.ConnectivityVerifier
import com.ganesh.twitterapp.data.repo.KeyValueHandler
import com.ganesh.twitterapp.data.repo.APIHelper
import com.ganesh.twitterapp.domain.TrendsInteractor
import com.ganesh.twitterapp.domain.TrendsUsecases
import com.google.android.gms.location.LocationRequest
import dagger.Module
import dagger.Provides
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider
import javax.inject.Singleton

@Module
class AppModule constructor(var app: Application) {


    @Provides
    @Singleton
    fun provideApp(): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideLocationRequest(): LocationRequest {
        return LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
            .setNumUpdates(1)
            .setInterval(1)
    }

    @Provides
    @Singleton
    fun providerTrendsInteractor(apiHelper: APIHelper, handler: KeyValueHandler): TrendsUsecases {
        return TrendsInteractor(apiHelper, handler)
    }


    @Provides
    @Singleton
    fun providerReactiveLocationProvider(): ReactiveLocationProvider {
        return ReactiveLocationProvider(app)
    }

    @Singleton
    @Provides
    fun getConnectivityChecker(): ConnectivityVerifier {
        return ConnectivityVerifier(app.applicationContext)
    }

    @Singleton
    @Provides
    fun provideSharedPref(): KeyValueHandler {

        val pref = app.applicationContext.applicationContext.getSharedPreferences(
            "TwitterApp", 0
        )

        val editor = pref.edit()

        return KeyValueHandler(pref, editor)
    }

}