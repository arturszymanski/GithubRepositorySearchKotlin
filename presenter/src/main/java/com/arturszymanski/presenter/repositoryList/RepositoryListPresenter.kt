package com.arturszymanski.presenter.repositoryList

import androidx.annotation.VisibleForTesting
import com.arturszymanski.domain.entity.Repository
import com.arturszymanski.domain.usecase.FetchRepositoryListUseCase
import com.arturszymanski.presenter.base.BasePresenter
import io.reactivex.rxkotlin.addTo
import timber.log.Timber
import javax.inject.Inject

class RepositoryListPresenter @Inject constructor(
    private val fetchRepositoryListUseCase: FetchRepositoryListUseCase
) : BasePresenter<RepositoryListView>() {

    lateinit var data: List<Repository>

    override fun onFirstBind() {
        super.onFirstBind()

        fetchRepositoryList()
    }

    override fun onViewRestoreState() {
        super.onViewRestoreState()

        if(::data.isInitialized) {
            present { it.displayData(data.toList()) }
        }
        else {
            fetchRepositoryList()
        }
    }

    //region UI
    fun itemSelected(position: Int, repository: Repository) {
        Timber.i("Item with position: $position, and value: $repository selected.")
        //TODO part of next task
    }
    //endregion

    //region fetchRepositoryListUseCase
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchRepositoryList() {
        Timber.i("Trying to fetch repository list")
        fetchRepositoryListUseCase
            .execute()
            .subscribe(
                this::fetchRepositoryListSuccess,
                this::fetchRepositoryListFailed
            )
            .addTo(compositeDisposable)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchRepositoryListSuccess(data: List<Repository>) {
        Timber.i("Fetch repository success with value: $data")
        this.data = data
        present { it.displayData(data.toList()) }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchRepositoryListFailed(throwable: Throwable) {
        Timber.e(throwable, "Failed to fetch repository list")
    }
    //endregion
}