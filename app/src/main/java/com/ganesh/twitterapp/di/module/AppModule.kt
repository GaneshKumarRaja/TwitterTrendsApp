package com.ganesh.twitterapp.di.module

import android.app.Application
import com.ganesh.twitterapp.util.ConnectivityVerifier
import com.ganesh.twitterapp.util.SheredPref
import com.google.android.gms.location.LocationRequest
import dagger.Module
import dagger.Provides
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider
import javax.inject.Singleton

@Module
class AppModule constructor(var app: Application) {

    @Provides
    @Singleton
    internal fun provideLocationRequest(): LocationRequest {

        return LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
            .setNumUpdates(1)
            .setInterval(1)

    }


    @Provides
    @Singleton
    internal fun providerReactiveLocationProvider(): ReactiveLocationProvider {
        return ReactiveLocationProvider(app)
    }

    @Singleton
    @Provides
     fun getConnectivityChecker(): ConnectivityVerifier {

        return ConnectivityVerifier(app.applicationContext)
    }

    @Singleton
    @Provides
     fun provideSharedPref(): SheredPref {
        return SheredPref(app.applicationContext, "TwitterApp")
    }

}