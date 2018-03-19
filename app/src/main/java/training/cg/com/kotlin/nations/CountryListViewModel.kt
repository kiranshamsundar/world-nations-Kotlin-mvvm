package training.cg.com.kotlin.nations

import android.databinding.Bindable
import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import rx.subjects.PublishSubject
import timber.log.Timber
import training.cg.com.kotlin.BR
import training.cg.com.kotlin.BaseViewModel
import training.cg.com.kotlin.nations.state.CountryListViewState
import training.cg.com.kotlin.nations.state.Response
import training.cg.com.kotlin.nations.state.State
import training.cg.com.kotlin.model.Country
import training.cg.com.kotlin.rest.repo.CountryRepository
import training.cg.com.kotlin.util.ConnectionObserver
import javax.inject.Inject

@BindingAdapter("dataSetChanged", "userClicks")
fun onDataSetChange(recycler: RecyclerView, countries: List<Country>?, userClicks: PublishSubject<Country>) {
    if (countries != null) {
        recycler.adapter = CountriesRecyclerAdapter(countries, userClicks)
        recycler.adapter.notifyDataSetChanged()
    }
}

class CountryListViewModel @Inject constructor(
        private val countryRepository: CountryRepository,
        connectionObserver: ConnectionObserver) : BaseViewModel<List<Country>>() {

    private val viewStates = PublishSubject.create<State<CountryListViewState>>()

    private var countries: List<Country>? = null
    private val userClicks: PublishSubject<Country> = PublishSubject.create()

    private var loading = false
    private var error = false
    private var errorMsg = ""
    private var connected = true

    init {
        viewStates.subscribe {
            when (it) {
                is CountryListViewState.Loading -> onLoading()
                is CountryListViewState.Success -> onContent(it.countries)
                is CountryListViewState.Error -> onError(it.message)
                is CountryListViewState.Connection -> onConnectivity(it.connected)
            }
        }

        connectionObserver.networkChanges()
                .subscribe { viewStates.onNext(CountryListViewState.Connection(it)) }

        loadData()

        userClicks.subscribe { Timber.i("User clicked ${it.name}") }
    }

    override fun onContent(content: List<Country>) {
        countries = content
        loading = false
        error = false
        notifyChange()
    }

    override fun onLoading() {
        loading = true
        error = false
        notifyPropertyChanged(BR.loading)
        notifyPropertyChanged(BR.error)
        notifyPropertyChanged(BR.reloadButtonEnabled)
    }

    override fun onError(message: String) {
        loading = false
        error = true
        errorMsg = message
        notifyPropertyChanged(BR.errorMessage)
        notifyPropertyChanged(BR.loading)
        notifyPropertyChanged(BR.error)
    }

    override fun onConnectivity(connected: Boolean) {
        this.connected = connected
        notifyPropertyChanged(BR.reloadButtonEnabled)
    }

    fun loadData() {
        viewStates.onNext(CountryListViewState.Loading())

        countryRepository.getData()
                .subscribe {
                    viewStates.onNext(when (it) {
                        is Response.Success -> CountryListViewState.Success(it.data)
                        is Response.Failure -> CountryListViewState.Error(it.reason)
                    })
                }
    }

    @Bindable fun getError(): Boolean = error
    @Bindable fun getErrorMessage(): String = errorMsg
    @Bindable fun getLoading(): Boolean = loading
    @Bindable fun getReloadButtonEnabled(): Boolean = connected && !loading

    fun getCountries(): List<Country>? = countries
    fun getUserClicks(): PublishSubject<Country> = userClicks
}