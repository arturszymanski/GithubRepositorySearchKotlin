package com.arturszymanski.githubrepositorysearchkotlin.di

import androidx.lifecycle.ViewModel
import com.arturszymanski.presenter.base.PresenterFactory
import com.arturszymanski.presenter.repositoryList.RepositoryListPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Provider


@Module
class PresenterModule {

    @Provides
    fun provideRPresenterFactory(presenterProvider: Provider<RepositoryListPresenter>) =
        object : PresenterFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> createPresenter(presenterClass: Class<T>): T {
                return presenterProvider.get() as T
            }
        }

}