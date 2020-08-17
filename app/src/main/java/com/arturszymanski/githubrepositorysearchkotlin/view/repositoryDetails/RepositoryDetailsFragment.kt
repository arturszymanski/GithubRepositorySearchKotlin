package com.arturszymanski.githubrepositorysearchkotlin.view.repositoryDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.arturszymanski.domain.entity.Repository
import com.arturszymanski.githubrepositorysearchkotlin.R
import com.arturszymanski.githubrepositorysearchkotlin.view.base.BackAware
import com.arturszymanski.githubrepositorysearchkotlin.view.base.BasePresenterFragment
import com.arturszymanski.presenter.base.PresenterFactory
import com.arturszymanski.presenter.di.REPOSITORY_DETAILS_PRESENTER_FACTORY
import com.arturszymanski.presenter.repositoryDetails.RepositoryDetailsPresenter
import com.arturszymanski.presenter.repositoryDetails.RepositoryDetailsView
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_repository_details.*
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

class RepositoryDetailsFragment :
    BasePresenterFragment<
            RepositoryDetailsPresenter,
            RepositoryDetailsView
            >(),
    RepositoryDetailsView,
    BackAware {

    @Named(REPOSITORY_DETAILS_PRESENTER_FACTORY)
    @Inject
    lateinit var presenterFactoryProvider: Provider<PresenterFactory>

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val args: RepositoryDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(
            R.layout.fragment_repository_details,
            container,
            false
        )

    //region View
    override fun displayRepository(repository: Repository) {
        (activity as? AppCompatActivity)
            ?.supportActionBar
            ?.title = repository.name

        watchersLabel.text = repository.watchersCount.toString()
        forksLabel.text = repository.forksCount.toString()
        issuesLabel.text = repository.openIssuesCount.toString()
    }

    override fun displayPrevious() =
        popBackstackOrFinishActivity()

    override fun displayReadme(readmeContent: String) {
        noReadmeLabel.visibility = View.GONE
        readmeWebView
            .loadDataWithBaseURL(
                null,
                readmeContent,
                "text/html",
                "utf-8",
                null
            )
    }

    override fun displayApiLimitError() =
        Snackbar
            .make(
                mainContainer,
                R.string.error_api_requests_limit,
                Snackbar.LENGTH_SHORT)
            .show()

    override fun displayReadmeNotFoundError() {
        noReadmeLabel.visibility = View.VISIBLE
    }
    //endregion

    //region BackAware
    override fun onBackPressed() {
        presenter.displayPreviousSelected()
    }
    //endregion

    //region Base
    override fun injectDependencies() =
        AndroidSupportInjection.inject(this)

    override fun onPresenterPrepared(fromStorage: Boolean) {
        if (fromStorage) {
            return
        }
        presenter.repository = args.repository
    }

    override fun presenterClass(): Class<RepositoryDetailsPresenter> =
        RepositoryDetailsPresenter::class.java

    override fun prepareFactory(): PresenterFactory =
        presenterFactoryProvider.get()

    //endregion
}