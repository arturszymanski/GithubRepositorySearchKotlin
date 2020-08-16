package com.arturszymanski.githubrepositorysearchkotlin.di

import android.content.Context
import com.arturszymanski.data.di.ApiModule
import com.arturszymanski.domain.di.DataSourceModule
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
        (ApiModule::class),
        (DataSourceModule::class),
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