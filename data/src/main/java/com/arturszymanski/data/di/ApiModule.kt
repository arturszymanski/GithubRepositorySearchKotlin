package com.arturszymanski.data.di

import com.arturszymanski.data.BuildConfig
import com.arturszymanski.data.network.GithubAPI
import com.arturszymanski.data.network.GithubReadmeAPI
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton

const val RAW_RESPONSE_RETROFIT = "RRR"
const val PARSED_RESPONSE_RETROFIT = "PRR"

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

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory
            .create(moshi)

    @Provides
    @Singleton
    fun provideScalarsConverterFactory(): ScalarsConverterFactory =
        ScalarsConverterFactory
            .create()

    @Named(PARSED_RESPONSE_RETROFIT)
    @Singleton
    @Provides
    fun provideParsedResponseRetrofit(
        okHttpClient: OkHttpClient,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        moshiConverterFactory: MoshiConverterFactory,
        apiPath: String
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .baseUrl(apiPath)
            .build()

    @Named(RAW_RESPONSE_RETROFIT)
    @Singleton
    @Provides
    fun provideRawResponseRetrofit(
        okHttpClient: OkHttpClient,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        scalarsConverterFactory: ScalarsConverterFactory,
        apiPath: String
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(scalarsConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .baseUrl(apiPath)
            .build()

    @Singleton
    @Provides
    fun provideGithubApi(@Named(PARSED_RESPONSE_RETROFIT) retrofit: Retrofit): GithubAPI =
        retrofit
            .create(GithubAPI::class.java)

    @Singleton
    @Provides
    fun provideGithubReadmeApi(@Named(RAW_RESPONSE_RETROFIT) retrofit: Retrofit): GithubReadmeAPI =
        retrofit
            .create(GithubReadmeAPI::class.java)
}