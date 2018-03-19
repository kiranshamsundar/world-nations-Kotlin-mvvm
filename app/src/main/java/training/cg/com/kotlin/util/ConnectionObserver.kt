package training.cg.com.kotlin.util

import rx.Observable

interface ConnectionObserver {

    fun networkChanges(): Observable<Boolean>
    fun getConnected(): Boolean
}