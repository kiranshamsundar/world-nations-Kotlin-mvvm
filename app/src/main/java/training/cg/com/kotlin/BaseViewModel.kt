package training.cg.com.kotlin

import android.databinding.BaseObservable

abstract class BaseViewModel<in Content> : BaseObservable() {

    abstract fun onLoading()
    abstract fun onContent(content: Content)
    abstract fun onError(message: String = "")
    abstract fun onConnectivity(connected: Boolean)
}