package com.arturszymanski.githubrepositorysearchkotlin.di

import android.content.Context
import com.arturszymanski.githubrepositorysearchkotlin.GithubRepositorySearchKotlinApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (AndroidInjectionModule::class),
        (ActivitiesModule::class),
        (FragmentsModule::class),
        (UiModule::class),
        (PresenterModule::class)
    ]
)
interface ApplicationComponent {

    fun inject(application: GithubRepositorySearchKotlinApplication)

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent

        @BindsInstance
        fun context(context: Context): Builder
    }
}