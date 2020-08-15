package com.arturszymanski.domain.usecase

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.runAsyncReturnOnMain() : Single<T> =
    this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())