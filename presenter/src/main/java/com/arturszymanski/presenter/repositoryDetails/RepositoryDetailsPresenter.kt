package com.arturszymanski.presenter.repositoryDetails

import com.arturszymanski.domain.entity.Repository
import com.arturszymanski.presenter.base.BasePresenter
import timber.log.Timber
import javax.inject.Inject

class RepositoryDetailsPresenter @Inject constructor() : BasePresenter<RepositoryDetailsView>() {

    lateinit var repository: Repository

    override fun onFirstBind() {
        super.onFirstBind()

        if (::repository.isInitialized.not()) error("Required parameter repository not initialized")

        present {
            it.displayRepository(repository)
        }
    }

    override fun onViewRestoreState() {
        super.onViewRestoreState()

        present {
            it.displayRepository(repository)
        }
    }

    //region UI
    fun displayPreviousSelected() {
        Timber.i("Display previous selected")
        present { it.displayPrevious() }
    }
    //endregion
}