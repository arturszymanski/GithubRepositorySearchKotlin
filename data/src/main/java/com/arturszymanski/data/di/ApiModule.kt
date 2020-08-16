package com.arturszymanski.data.di

import com.arturszymanski.data.BuildConfig
import com.arturszymanski.data.network.GithubAPI
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    fun provideBackendApiPath(): String =
        BuildConfig.API_PATH

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi
        .Builder()
        .build()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Timber.i(it)
        }).setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideOnBoardingOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRxCallAdapterFactory() =
        RxJava2CallAdapterFactory.create()

    @Singleton
    @Provides
    fun provideAuthorizeRetrofit(
        okHttpClient: OkHttpClient,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        moshi: Moshi,
        apiPath: String
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .baseUrl(apiPath)
            .build()

    @Singleton
    @Provides
    fun provideGithubApi(retrofit: Retrofit): GithubAPI =
        retrofit
            .create(GithubAPI::class.java)
}