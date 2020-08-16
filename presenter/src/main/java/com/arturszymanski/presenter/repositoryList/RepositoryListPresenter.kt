package com.arturszymanski.presenter.repositoryList

import androidx.annotation.VisibleForTesting
import com.arturszymanski.domain.entity.Page
import com.arturszymanski.domain.entity.Repository
import com.arturszymanski.domain.entity.SearchQuery
import com.arturszymanski.domain.usecase.FetchRepositoryListUseCase
import com.arturszymanski.presenter.base.BasePresenter
import io.reactivex.rxkotlin.addTo
import timber.log.Timber
import javax.inject.Inject

class RepositoryListPresenter @Inject constructor(
    private val fetchRepositoryListUseCase: FetchRepositoryListUseCase
) : BasePresenter<RepositoryListView>() {

    val data: MutableList<Repository> = mutableListOf()
    lateinit var lastPage: Page<Repository>

    var currentPage: Int = 0
    var searchQuery: SearchQuery = SearchQuery("")

    override fun onFirstBind() {
        super.onFirstBind()

        fetchRepositoryList(
            page = currentPage,
            searchQuery = searchQuery
        )
    }

    override fun onViewRestoreState() {
        super.onViewRestoreState()

        if(::lastPage.isInitialized) {
            present { it.displayData(this.data.toList()) }
        }
        else {
            fetchRepositoryList(
                page = currentPage,
                searchQuery = searchQuery
            )
        }
    }

    //region UI
    fun itemSelected(position: Int, repository: Repository) {
        Timber.i("Item with position: $position, and value: $repository selected.")
        //TODO part of next task
    }

    fun loadMoreSelected() {
        Timber.i("User selected to load more notifications")
        if (!::lastPage.isInitialized) {
            return
        }

        if (lastPage.totalCount <= data.size) {
            return
        }

        currentPage++

        fetchRepositoryList(
            page = currentPage,
            searchQuery = searchQuery
        )
    }
    //endregion

    //region fetchRepositoryListUseCase
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchRepositoryList(
        page: Int,
        searchQuery: SearchQuery
    ) {
        Timber.i("Trying to fetch repository list for page: $page and search query: $searchQuery")
        present { it.showProgress() }
        fetchRepositoryListUseCase
            .execute(
                page = page,
                searchQuery = searchQuery
            )
            .subscribe(
                this::fetchRepositoryListSuccess,
                this::fetchRepositoryListFailed
            )
            .addTo(compositeDisposable)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchRepositoryListSuccess(data: Page<Repository>) {
        Timber.i("Fetch repository success with value: $data")
        this.lastPage = data
        val newData = data
            .items
            .filter { newItem ->
                this.data.firstOrNull { oldItem -> newItem.id == oldItem.id } == null
            }
        this.data.addAll(newData)
        present {
            it.hideProgress()
            it.displayData(this.data.toList())
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchRepositoryListFailed(throwable: Throwable) {
        Timber.e(throwable, "Failed to fetch repository list")
        present {
            it.hideProgress()
        }
    }
    //endregion
}