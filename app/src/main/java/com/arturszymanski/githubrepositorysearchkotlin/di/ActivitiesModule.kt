package com.arturszymanski.githubrepositorysearchkotlin.di

import com.arturszymanski.githubrepositorysearchkotlin.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}