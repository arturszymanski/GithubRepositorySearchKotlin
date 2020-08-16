package com.arturszymanski.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepositoryDTO(
    val id: Long,
    val name: String,
    val owner: OwnerDTO,
    @field:Json(name = "fork")
    val isFork: Boolean,
    @field:Json(name = "private")
    val isPrivate: Boolean,
    @field:Json(name = "forks_count")
    val forksCount: Long,
    @field:Json(name = "watchers_count")
    val watchersCount: Long,
    @field:Json(name = "open_issues_count")
    val openIssuesCount: Long
)