package com.arturszymanski.domain.mapper

import com.arturszymanski.domain.entity.error.ForbidenException
import com.arturszymanski.domain.entity.error.NotFoundException
import com.arturszymanski.domain.entity.error.NotImplementedException
import com.arturszymanski.domain.entity.error.ServerException
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.HttpException
import javax.inject.Inject


class HttpErrorMapper @Inject constructor() {

    fun <T> mapSingle(throwable: Throwable): Single<T> {
        val exception = handleException(throwable)
        return Single.error<T>(exception)
    }

    fun <T> mapObservable(throwable: Throwable): Observable<T> {
        val exception = handleException(throwable)
        return Observable.error<T>(exception)
    }

    fun <T> mapFlowable(throwable: Throwable): Flowable<T> {
        val exception = handleException(throwable)
        return Flowable.error<T>(exception)
    }

    fun mapCompletable(throwable: Throwable): Completable {
        val exception = handleException(throwable)
        return Completable.error(exception)
    }

    private fun handleException(throwable: Throwable): Throwable {
        if (throwable !is HttpException) {
            //We what to handle only Http errors.
            return throwable
        }

        return when (throwable.code()) {
            500 -> ServerException("Any server error, message: ${throwable.message()}, response: ${throwable.response()}")
            404 -> NotFoundException("Content not found error, message: ${throwable.message()}, response: ${throwable.response()}")
            403 -> ForbidenException("Access forbidden, message: ${throwable.message()}, response: ${throwable.response()}")
            else -> NotImplementedException("This error is not yet implemented, message: ${throwable.message()}, response: ${throwable.response()}")
        }
    }
}