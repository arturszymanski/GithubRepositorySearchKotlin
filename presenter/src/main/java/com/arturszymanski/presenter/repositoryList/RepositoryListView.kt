package com.arturszymanski.presenter.repositoryList

import com.arturszymanski.domain.entity.Repository
import com.arturszymanski.presenter.base.BaseView

interface RepositoryListView: BaseView {

    fun displayData(data: List<Repository>)

    fun displayRepositoryDetails(repository: Repository)

    fun displayPrevious()
}