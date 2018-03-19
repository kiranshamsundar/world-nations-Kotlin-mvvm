package training.cg.com.kotlin.util

import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun networkErrorHandler(error: Throwable, func: (error: Throwable) -> Unit = {}) {
    Timber.e(error, "OnError")
    if (error is UnknownHostException || error is SocketTimeoutException) {
        // TODO Need to handle for these cases
    }
    return func(error)
}

val defaultOnError: (error: Throwable) -> Unit = { Timber.e(it, "OnError") }