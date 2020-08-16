package com.arturszymanski.domain.datasource

import com.arturszymanski.data.network.GithubAPI
import com.arturszymanski.domain.entity.Page
import com.arturszymanski.domain.entity.Repository
import com.arturszymanski.domain.entity.SearchQuery
import com.arturszymanski.domain.mapper.PageMapper
import com.arturszymanski.domain.mapper.RepositoryMapper
import com.arturszymanski.domain.mapper.toDTO
import io.reactivex.Single
import javax.inject.Inject

class RepositoryDataSourceImpl @Inject constructor(
    private val githubAPI: GithubAPI,
    private val pageMapper: PageMapper,
    private val repositoryMapper: RepositoryMapper
) : RepositoryDataSource {

    override fun fetchRepositoryList(
        page: Int,
        searchQuery: SearchQuery
    ): Single<Page<Repository>> =
        githubAPI
            .fetchRepositoryList(
                page = page,
                searchQuery = searchQuery.toDTO()
            )
            .map {
                pageMapper
                    .map(
                        it,
                        repositoryMapper::toDomain
                    )
            }
}