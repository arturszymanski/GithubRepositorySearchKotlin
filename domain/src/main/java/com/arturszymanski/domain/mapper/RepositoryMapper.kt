package com.arturszymanski.domain.mapper

import com.arturszymanski.data.entity.RepositoryDTO
import com.arturszymanski.domain.entity.Repository
import javax.inject.Inject

class RepositoryMapper @Inject constructor() {

    fun toDomain(repositoryDTO: RepositoryDTO): Repository =
        Repository(
            id = repositoryDTO.id,
            watchersCount = repositoryDTO.watchersCount,
            openIssuesCount = repositoryDTO.openIssuesCount,
            forksCount = repositoryDTO.forksCount,
            owner = repositoryDTO.owner.toDomain(),
            isFork = repositoryDTO.isFork,
            isPrivate = repositoryDTO.isPrivate,
            name = repositoryDTO.name
        )
}