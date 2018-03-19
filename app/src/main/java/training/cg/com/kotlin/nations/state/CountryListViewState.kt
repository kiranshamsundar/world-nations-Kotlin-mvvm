package training.cg.com.kotlin.nations.state

import training.cg.com.kotlin.model.Country

sealed class CountryListViewState : State<CountryListViewState> {

    class Idle: CountryListViewState()
    class Loading: CountryListViewState()
    data class Success(val countries: List<Country>): CountryListViewState()
    class Error(val message: String): CountryListViewState()
    class Connection(val connected: Boolean): CountryListViewState()
}