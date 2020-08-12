package com.arturszymanski.githubrepositorysearchkotlin.di

import com.arturszymanski.githubrepositorysearchkotlin.view.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {
    @ContributesAndroidInjector
    abstract fun bindSplashFragment(): SplashFragment
}