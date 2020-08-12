package com.arturszymanski.githubrepositorysearchkotlin

import android.app.Application
import com.arturszymanski.githubrepositorysearchkotlin.di.DaggerApplicationComponent
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class GithubRepositorySearchKotlinApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent.builder()
            .context(this)
            .build()
            .inject(this)

        val builder: Picasso.Builder = Picasso.Builder(baseContext)
            .indicatorsEnabled(false)
            .downloader(OkHttp3Downloader(baseContext))
        builder.listener { _, _, exception -> exception.printStackTrace() }

        if (BuildConfig.DEBUG) {
            builder.loggingEnabled(true)

            Timber.plant(Timber.DebugTree())

            AndroidThreeTen.init(this)
        }

        val picasso: Picasso = builder.build()
        Picasso.setSingletonInstance(picasso)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}
