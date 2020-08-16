package com.arturszymanski.presenter.di

import androidx.lifecycle.ViewModel
import com.arturszymanski.presenter.base.PresenterFactory
import com.arturszymanski.presenter.repositoryDetails.RepositoryDetailsPresenter
import com.arturszymanski.presenter.repositoryList.RepositoryListPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Provider

const val REPOSITORY_LIST_PRESENTER_FACTORY: String = "RLPF"
const val REPOSITORY_DETAILS_PRESENTER_FACTORY: String = "RDPF"

@Module
class PresenterModule {

    @Named(REPOSITORY_LIST_PRESENTER_FACTORY)
    @Provides
    fun provideRepositoryListPresenterFactory(presenterProvider: Provider<RepositoryListPresenter>) : PresenterFactory =
        object : PresenterFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> createPresenter(presenterClass: Class<T>): T {
                return presenterProvider.get() as T
            }
        }

    @Named(REPOSITORY_DETAILS_PRESENTER_FACTORY)
    @Provides
    fun provideRepositoryDetailsPresenterFactory(presenterProvider: Provider<RepositoryDetailsPresenter>) : PresenterFactory =
        object : PresenterFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> createPresenter(presenterClass: Class<T>): T {
                return presenterProvider.get() as T
            }
        }

}