package com.arturszymanski.domain.datasource

import com.arturszymanski.domain.entity.Page
import com.arturszymanski.domain.entity.Repository
import com.arturszymanski.domain.entity.SearchQuery
import io.reactivex.Single

interface RepositoryDataSource {

    fun fetchRepositoryList(
        page: Int,
        searchQuery: SearchQuery
    ): Single<Page<Repository>>

    fun fetchRepositoryDefaultReadme(
        ownerLogin: String,
        repositoryName: String
    ): Single<String>
}