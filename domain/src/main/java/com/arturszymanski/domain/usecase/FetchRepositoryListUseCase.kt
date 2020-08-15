package com.arturszymanski.domain.usecase

import com.arturszymanski.domain.entity.Owner
import com.arturszymanski.domain.entity.Repository
import io.reactivex.Single
import javax.inject.Inject

class FetchRepositoryListUseCase @Inject constructor() {

    fun execute() : Single<List<Repository>> =
        Single
            .just(
                listOf(
                    Repository(
                        id = 1L,
                        name = "Funny repo",
                        isPrivate = false,
                        isFork = false,
                        owner = Owner(
                            id = 0L,
                            login = "johnDoe"
                        ),
                        forksCount = 1L,
                        openIssuesCount = 20L,
                        watchersCount = 2L
                    ),
                    Repository(
                        id = 2L,
                        name = "Best App",
                        isPrivate = true,
                        isFork = true,
                        owner = Owner(
                            id = 1L,
                            login = "Chris Berk"
                        ),
                        forksCount = 5L,
                        openIssuesCount = 2L,
                        watchersCount = 10L
                    )
                )
            )
            .runAsyncReturnOnMain()
}