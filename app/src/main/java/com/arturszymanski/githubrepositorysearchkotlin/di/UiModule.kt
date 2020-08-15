package com.arturszymanski.githubrepositorysearchkotlin.di

import com.arturszymanski.githubrepositorysearchkotlin.view.reporitoryList.RepositoryListViewHolderFactory
import dagger.Module
import dagger.Provides

@Module
class UiModule {

    @Provides
    fun provideRepositoryListViewHolderFactory() =
        RepositoryListViewHolderFactory()
}