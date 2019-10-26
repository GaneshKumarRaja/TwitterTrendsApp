package com.ganesh.twitterapp.di

import androidx.lifecycle.ViewModelProvider
import com.ganesh.twitterapp.di.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory

}
