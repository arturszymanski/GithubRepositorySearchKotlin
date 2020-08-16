package com.arturszymanski.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GithubReadmeAPI {
    @Headers("Accept: application/vnd.github.VERSION.html")
    @GET("/repos/{ownerLogin}/{repositoryName}/readme")
    fun fetchRepositoryDefaultReadme(
        @Path("ownerLogin") ownerLogin: String,
        @Path("repositoryName") repositoryName: String
    ): Single<String>
}