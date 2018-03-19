package training.cg.com.kotlin.main

import android.databinding.BaseObservable
import android.view.View
import rx.Observable
import rx.subjects.PublishSubject
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseObservable() {

    private val countryClicks = PublishSubject.create<View>()

    val countriesButtonText = "Countries"


    fun onCountryLabelClicks(view: View) {
        countryClicks.onNext(view)
    }


    fun getCountryListFragmentEvents(): Observable<View> = countryClicks.asObservable()
}