package com.arturszymanski.githubrepositorysearchkotlin.view.reporitoryList

import android.os.Bundle
import android.view.*
import com.arturszymanski.domain.entity.Repository
import com.arturszymanski.githubrepositorysearchkotlin.R
import com.arturszymanski.githubrepositorysearchkotlin.view.base.BasePresenterFragment
import com.arturszymanski.githubrepositorysearchkotlin.view.util.PaginationActionsListener
import com.arturszymanski.githubrepositorysearchkotlin.view.util.PaginationScrollListener
import com.arturszymanski.presenter.base.PresenterFactory
import com.arturszymanski.presenter.repositoryList.RepositoryListPresenter
import com.arturszymanski.presenter.repositoryList.RepositoryListView
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_repository_list.*
import javax.inject.Inject
import javax.inject.Provider
import androidx.appcompat.widget.SearchView


class RepositoryListFragment :
    BasePresenterFragment<
            RepositoryListPresenter,
            RepositoryListView
            >(),
    RepositoryListView,
    PaginationActionsListener {

    @Inject
    lateinit var paginationScrollListener: PaginationScrollListener

    @Inject
    lateinit var adapter: RepositoryListAdapter

    @Inject
    lateinit var presenterFactoryProvider: Provider<PresenterFactory>

    val repositoryListItemInteractions =
        object : RepositoryListItemInteractions {
            override fun itemSelected(position: Int, repository: Repository) {
                presenter.itemSelected(
                    position = position,
                    repository = repository
                )
            }
        }

    val onSearchQueryTextChangeListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            presenter.searchQueryConfirmed(query ?: "")
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            presenter.searchQueryChanged(newText ?: "")
            return false
        }
    }

    val onSearchCloseListener = SearchView.OnCloseListener {
        presenter.searchQueryConfirmed("")
        false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(
            R.layout.fragment_repository_list,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.itemInteractions = repositoryListItemInteractions
        repositoryList.adapter = adapter

        paginationScrollListener.action = this
        repositoryList.addOnScrollListener(paginationScrollListener)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

        val myActionMenuItem = menu.findItem(R.id.action_search)
        with(myActionMenuItem.actionView as SearchView) {
            setOnQueryTextListener(onSearchQueryTextChangeListener)
            setOnCloseListener(onSearchCloseListener)
        }
    }

    //region View
    override fun displayData(data: List<Repository>) {
        adapter.updateData(data)
    }

    override fun showProgress() {
        paginationScrollListener.isDataLoading = true
        super.showProgress()
    }

    override fun hideProgress() {
        super.hideProgress()
        paginationScrollListener.isDataLoading = false
    }
    //endregion

    //region PaginationActionsListener
    override fun loadMore() {
        presenter.loadMoreSelected()
    }
    //endregion

    //region Base
    override fun injectDependencies() =
        AndroidSupportInjection.inject(this)

    override fun onPresenterPrepared(fromStorage: Boolean) {}

    override fun presenterClass(): Class<RepositoryListPresenter> =
        RepositoryListPresenter::class.java

    override fun prepareFactory(): PresenterFactory =
        presenterFactoryProvider.get()

    //endregion
}