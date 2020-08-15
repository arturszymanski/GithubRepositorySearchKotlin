package com.arturszymanski.githubrepositorysearchkotlin.view.reporitoryList

import com.arturszymanski.domain.entity.Repository

interface RepositoryListItemInteractions {

    fun itemSelected(position: Int, repository: Repository)
}