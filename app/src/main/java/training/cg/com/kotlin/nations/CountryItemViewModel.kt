package training.cg.com.kotlin.nations

import android.databinding.BaseObservable
import rx.subjects.PublishSubject
import training.cg.com.kotlin.model.Country

class CountryItemViewModel(private val country: Country, private val userClicks: PublishSubject<Country>) : BaseObservable() {

    fun getCountryName(): String = country.name
    fun getCountryCapital(): String=country.capital
    fun getFlagUrl() = country.flag
    fun getCountrynameFont() = "coolvetica.ttf"
    fun getCountryCapitalFont()="GeosansLight.ttf"

    fun userClick() {
        userClicks.onNext(country)
    }
}