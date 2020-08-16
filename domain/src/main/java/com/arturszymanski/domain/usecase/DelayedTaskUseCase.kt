package com.arturszymanski.domain.usecase

import io.reactivex.Completable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DelayedTaskUseCase @Inject constructor() {

    fun execute(delay: Long, timeUnit: TimeUnit): Completable {
        return Completable.complete()
            .delay(delay, timeUnit)
            .runAsyncReturnOnMain()
    }
}