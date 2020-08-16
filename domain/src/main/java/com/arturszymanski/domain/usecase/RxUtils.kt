package com.arturszymanski.domain.usecase

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.runAsyncReturnOnMain() : Single<T> =
    this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun Completable.runAsyncReturnOnMain() : Completable =
    this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())