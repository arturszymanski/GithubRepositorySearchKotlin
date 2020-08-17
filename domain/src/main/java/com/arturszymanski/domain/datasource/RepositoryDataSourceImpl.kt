package com.arturszymanski.domain.datasource

import com.arturszymanski.data.network.GithubAPI
import com.arturszymanski.data.network.GithubReadmeAPI
import com.arturszymanski.domain.entity.Page
import com.arturszymanski.domain.entity.Repository
import com.arturszymanski.domain.entity.SearchQuery
import com.arturszymanski.domain.mapper.HttpErrorMapper
import com.arturszymanski.domain.mapper.PageMapper
import com.arturszymanski.domain.mapper.RepositoryMapper
import com.arturszymanski.domain.mapper.toDTO
import io.reactivex.Single
import javax.inject.Inject

class RepositoryDataSourceImpl @Inject constructor(
    private val githubAPI: GithubAPI,
    private val githubReadmeAPI: GithubReadmeAPI,
    private val httpErrorMapper: HttpErrorMapper,
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
            .onErrorResumeNext { httpErrorMapper.mapSingle(it) }
            .map {
                pageMapper
                    .map(
                        it,
                        repositoryMapper::toDomain
                    )
            }

    override fun fetchRepositoryDefaultReadme(
        ownerLogin: String,
        repositoryName: String
    ): Single<String> =
        githubReadmeAPI
            .fetchRepositoryDefaultReadme(
                ownerLogin = ownerLogin,
                repositoryName = repositoryName
            )
            .onErrorResumeNext { httpErrorMapper.mapSingle(it) }
}