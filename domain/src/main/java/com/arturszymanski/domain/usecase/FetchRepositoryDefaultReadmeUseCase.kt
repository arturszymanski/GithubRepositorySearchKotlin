package com.arturszymanski.domain.usecase

import com.arturszymanski.domain.datasource.RepositoryDataSource
import io.reactivex.Single
import javax.inject.Inject

class FetchRepositoryDefaultReadmeUseCase @Inject constructor(
    private val repositoryDataSource: RepositoryDataSource
) {

    fun execute(
        ownerLogin: String,
        repositoryName: String
    ): Single<String> =
        repositoryDataSource
            .fetchRepositoryDefaultReadme(
                ownerLogin = ownerLogin,
                repositoryName = repositoryName
            )
            .runAsyncReturnOnMain()
}