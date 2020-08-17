package com.arturszymanski.presenter.repositoryDetails

import com.arturszymanski.domain.entity.Repository
import com.arturszymanski.presenter.base.BaseView

interface RepositoryDetailsView : BaseView {

    fun displayRepository(repository: Repository)

    fun displayPrevious()

    fun displayReadme(readmeContent: String)

    fun displayApiLimitError()

    fun displayReadmeNotFoundError()
}