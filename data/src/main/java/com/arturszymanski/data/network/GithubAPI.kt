package com.arturszymanski.data.network

import com.arturszymanski.data.BuildConfig
import com.arturszymanski.data.entity.PageDTO
import com.arturszymanski.data.entity.RepositoryDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubAPI {

    @GET("/search/repositories")
    fun fetchRepositoryList(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int = BuildConfig.DEFAULT_PAGE_SIZE,
        @Query("q") searchQuery: String
    ): Single<PageDTO<RepositoryDTO>>
}