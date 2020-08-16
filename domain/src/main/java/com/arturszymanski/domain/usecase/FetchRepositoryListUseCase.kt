package com.arturszymanski.domain.usecase

import com.arturszymanski.domain.datasource.RepositoryDataSource
import com.arturszymanski.domain.entity.Owner
import com.arturszymanski.domain.entity.Page
import com.arturszymanski.domain.entity.Repository
import com.arturszymanski.domain.entity.SearchQuery
import io.reactivex.Single
import javax.inject.Inject

class FetchRepositoryListUseCase @Inject constructor(
    private val repositoryDataSource: RepositoryDataSource
) {

    fun execute(
        page: Int,
        searchQuery: SearchQuery
    ) : Single<Page<Repository>> =
        repositoryDataSource
            .fetchRepositoryList(
                page = page,
                searchQuery = searchQuery
            )
            .runAsyncReturnOnMain()
}