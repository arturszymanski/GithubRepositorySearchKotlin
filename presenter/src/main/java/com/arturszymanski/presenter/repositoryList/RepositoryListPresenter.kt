package com.arturszymanski.presenter.repositoryList

import androidx.annotation.VisibleForTesting
import com.arturszymanski.domain.entity.Page
import com.arturszymanski.domain.entity.Repository
import com.arturszymanski.domain.entity.SearchQuery
import com.arturszymanski.domain.usecase.DelayedTaskUseCase
import com.arturszymanski.domain.usecase.FetchRepositoryListUseCase
import com.arturszymanski.presenter.base.BasePresenter
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val SEARCH_DELAY_MILISECONDS = 500L

class RepositoryListPresenter @Inject constructor(
    private val fetchRepositoryListUseCase: FetchRepositoryListUseCase,
    private val delayedTaskUseCase: DelayedTaskUseCase
) : BasePresenter<RepositoryListView>() {

    val data: MutableList<Repository> = mutableListOf()
    lateinit var lastPage: Page<Repository>

    var currentPage: Int = 0
    var searchQuery: SearchQuery = SearchQuery("")

    override fun onFirstBind() {
        super.onFirstBind()

        fetchRepositoryList(
            page = currentPage,
            searchQuery = searchQuery,
            operationType = RepositoryListFetchType.DEFAULT
        )
    }

    override fun onViewRestoreState() {
        super.onViewRestoreState()

        if(::lastPage.isInitialized) {
            present { it.displayData(prepareRepositoryListToDisplay()) }
        }
        else {
            fetchRepositoryList(
                page = currentPage,
                searchQuery = searchQuery,
                operationType = RepositoryListFetchType.DEFAULT
            )
        }
    }

    //region UI
    fun itemSelected(position: Int, repository: Repository) {
        Timber.i("Item with position: $position, and value: $repository selected.")
        //TODO part of next task
    }

    fun searchQueryChanged(searchQueryText: String) {
        Timber.i("Search query changed with value: $searchQueryText")
        delaySplashScreen(searchQueryText = searchQueryText)
    }

    fun searchQueryConfirmed(searchQueryText: String) {
        Timber.i("Search query confirmed with value: $searchQueryText")
        searchWithoutDelayIfNotSearched(searchQueryText = searchQueryText)
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
            searchQuery = searchQuery,
            operationType = RepositoryListFetchType.LOAD_MORE
        )
    }
    //endregion

    //region FetchRepositoryListUseCase
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchRepositoryList(
        page: Int,
        searchQuery: SearchQuery,
        operationType: RepositoryListFetchType
    ) {
        Timber.i("Trying to fetch repository list for page: $page and search query: $searchQuery")
        present { it.showProgress() }
        fetchRepositoryListUseCase
            .execute(
                page = page,
                searchQuery = searchQuery
            )
            .subscribe(
                {
                    fetchRepositoryListSuccess(
                        data = it,
                        operationType = operationType
                    )
                },
                this::fetchRepositoryListFailed
            )
            .addTo(compositeDisposable)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchRepositoryListSuccess(
        data: Page<Repository>,
        operationType: RepositoryListFetchType
    ) {
        Timber.i("Fetch repository success with value: $data")
        when(operationType) {
            RepositoryListFetchType.LOAD_MORE -> loadMore(data = data)
            RepositoryListFetchType.SEARCH -> search(data = data)
            RepositoryListFetchType.DEFAULT -> loadMore(data = data)
        }

    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun search(data: Page<Repository>) {
        this.lastPage = data
        this.data.clear()
        this.data.addAll(data.items)
        present {
            it.hideProgress()
            it.displayData(prepareRepositoryListToDisplay())
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun loadMore(data: Page<Repository>) {
        this.lastPage = data
        val newData = data
            .items
            .filter { newItem ->
                this.data.firstOrNull { oldItem -> newItem.id == oldItem.id } == null
            }
        this.data.addAll(newData)
        present {
            it.hideProgress()
            it.displayData(prepareRepositoryListToDisplay())
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

    //region DelayedTaskUseCase
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var delayedSearchDisposable: Disposable? = null
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun delaySplashScreen(searchQueryText: String) {
        Timber.i("Trying to delay search for: $searchQueryText")
        delayedSearchDisposable
            ?.let {
                if(it.isDisposed.not()) {
                    it.dispose()
                }
            }

        delayedSearchDisposable = delayedTaskUseCase.execute(SEARCH_DELAY_MILISECONDS, TimeUnit.MILLISECONDS)
            .subscribe(
                { delaySplashScreenSuccess(searchQueryText) },
                this::delaySplashScreenFailed
            )
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun delaySplashScreenSuccess(searchQueryText: String) {
        Timber.i("Search delayed successfully")
        searchWithoutDelayIfNotSearched(searchQueryText = searchQueryText)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun delaySplashScreenFailed(throwable: Throwable) {
        Timber.e(throwable,"Failed to delay search")
    }
    //endregion

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun searchWithoutDelayIfNotSearched(searchQueryText: String) {
        Timber.i("Trying to search without delay for: $searchQueryText")
        if(searchQueryText == searchQuery.name) {
            return
        }

        searchQuery = searchQuery.copy(searchQueryText)
        currentPage = 0
        fetchRepositoryList(
            page = currentPage,
            searchQuery = searchQuery,
            operationType = RepositoryListFetchType.SEARCH
        )
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun prepareRepositoryListToDisplay(): List<Repository> {
        Timber.i("Preparing data to display")
        return data.toList()
    }
}

enum class RepositoryListFetchType {
    LOAD_MORE,
    SEARCH,
    DEFAULT
}