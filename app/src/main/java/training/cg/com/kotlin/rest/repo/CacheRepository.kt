package training.cg.com.kotlin.rest.repo

import training.cg.com.kotlin.nations.state.Response
import training.cg.com.kotlin.extension.isNetworkException
import training.cg.com.kotlin.util.ConnectionObserver
import rx.Observable
import rx.schedulers.Schedulers
import rx.subjects.PublishSubject

abstract class CacheRepository<T> (private val connectionObserver: ConnectionObserver) {

    private val responseEvents: PublishSubject<Response<T>> = PublishSubject.create()

    private val forceNetworkMap: (T, Boolean) -> Observable<T> = { data, forceNetwork ->
        if (forceNetwork) Observable.just(null) else Observable.just(data)
    }

    protected open val memoryCall: Observable<T> = Observable.just(null)
    protected open val cacheCall: Observable<T> = Observable.just(null)
    abstract protected val restCall: Observable<T>

    protected open fun serialize(response: T) {}
    protected open fun keepMemory(response: T?) {}

    fun getData(forceNetwork: Boolean = false): Observable<Response<T>> {
        Observable.concat(
                memoryCall.flatMap { forceNetworkMap(it, forceNetwork) },
                cacheCall.subscribeOn(Schedulers.computation())
                        .flatMap { forceNetworkMap(it, forceNetwork) }
                        .doOnNext { keepMemory(it) },
                restCall.subscribeOn(Schedulers.io())
                        .retryWhen { errors -> errors.flatMap { error: Throwable ->
                            if (error.isNetworkException()) {
                                responseEvents.onNext(Response.Failure("No Network"))
                                connectionObserver.networkChanges().filter { it }.first()
                            } else {
                                Observable.error<Throwable>(error)
                            }
                        } }
                        .doOnNext { serialize(it) })
                .takeFirst { it != null }
                .subscribe(
                        { responseEvents.onNext(Response.Success(it)) },
                        { e: Throwable -> responseEvents.onNext(Response.Failure(e.localizedMessage)) })

        return responseEvents.asObservable()
    }
}