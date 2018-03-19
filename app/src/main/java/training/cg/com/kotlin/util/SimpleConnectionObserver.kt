package training.cg.com.kotlin.util

import android.content.Context
import android.net.ConnectivityManager
import com.jakewharton.rxrelay.BehaviorRelay
import rx.Observable
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

class SimpleConnectionObserver(context: Context) : ConnectionObserver {

    init {
        Observable.interval(1000L, TimeUnit.MILLISECONDS)
                .takeWhile { weakContext.get() != null }
                .flatMap { Observable.just(weakContext.get()) }
                .flatMap {
                    it?.let {
                        val connectiviyManager = it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                        val networkInfo = connectiviyManager.activeNetworkInfo
                        Observable.just(networkInfo != null && networkInfo.isConnectedOrConnecting)
                    } ?: Observable.just(false)
                }
                .distinctUntilChanged()
                .subscribe { connectivityChanges.call(it) }
    }

    private val weakContext = WeakReference<Context>(context)

    private val connectivityChanges = BehaviorRelay.create<Boolean>()

    override fun networkChanges(): Observable<Boolean> = connectivityChanges.asObservable()

    override fun getConnected(): Boolean = connectivityChanges.value
}