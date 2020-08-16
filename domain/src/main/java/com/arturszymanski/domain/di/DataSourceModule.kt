package com.arturszymanski.domain.di

import com.arturszymanski.domain.datasource.RepositoryDataSource
import com.arturszymanski.domain.datasource.RepositoryDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class DataSourceModule {

    @Binds
    abstract fun bindRepositoryDataSource(repositoryDataSourceImpl: RepositoryDataSourceImpl) : RepositoryDataSource

}