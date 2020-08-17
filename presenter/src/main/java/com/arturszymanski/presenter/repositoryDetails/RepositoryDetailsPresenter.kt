package com.arturszymanski.presenter.repositoryDetails

import androidx.annotation.VisibleForTesting
import com.arturszymanski.domain.entity.Repository
import com.arturszymanski.domain.usecase.FetchRepositoryDefaultReadmeUseCase
import com.arturszymanski.presenter.base.BasePresenter
import io.reactivex.rxkotlin.addTo
import timber.log.Timber
import javax.inject.Inject

class RepositoryDetailsPresenter @Inject constructor(
    private val fetchRepositoryDefaultReadmeUseCase: FetchRepositoryDefaultReadmeUseCase
) : BasePresenter<RepositoryDetailsView>() {

    lateinit var repository: Repository

    lateinit var readmeContent: String

    override fun onFirstBind() {
        super.onFirstBind()

        if (::repository.isInitialized.not()) error("Required parameter repository not initialized")

        present {
            it.displayRepository(repository)
        }
        fetchRepositoryDefaultReadme(
            ownerLogin = repository.owner.login,
            repositoryName = repository.name
        )
    }

    override fun onViewRestoreState() {
        super.onViewRestoreState()

        present {
            it.displayRepository(repository)
        }

        if (::readmeContent.isInitialized) {
            present {
                it.displayReadme(readmeContent)
            }
        } else {
            fetchRepositoryDefaultReadme(
                ownerLogin = repository.owner.login,
                repositoryName = repository.name
            )
        }
    }

    //region UI
    fun displayPreviousSelected() {
        Timber.i("Display previous selected")
        present { it.displayPrevious() }
    }
    //endregion

    //region FetchRepositoryDefaultReadmeUseCase
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchRepositoryDefaultReadme(
        ownerLogin: String,
        repositoryName: String
    ) {
        Timber.i("Trying to fetch default readme that belongs to: $ownerLogin and comes from repository: $repositoryName")
        present {
            it.showProgress()
        }
        fetchRepositoryDefaultReadmeUseCase
            .execute(
                ownerLogin = ownerLogin,
                repositoryName = repositoryName
            )
            .subscribe(
                this::fetchRepositoryDefaultReadmeSuccess,
                this::fetchRepositoryDefaultReadmeFailed
            )
            .addTo(compositeDisposable)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchRepositoryDefaultReadmeSuccess(readmeContent: String) {
        Timber.i("Repository default readme content fetched successfully with value: $readmeContent")
        this.readmeContent = readmeContent
        present {
            it.displayReadme(readmeContent)
            it.hideProgress()
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchRepositoryDefaultReadmeFailed(throwable: Throwable) {
        Timber.e(throwable, "Failed to fetch repository default content")
        present {
            it.hideProgress()
        }
    }
    //endregion
}