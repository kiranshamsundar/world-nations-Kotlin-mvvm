package training.cg.com.kotlin.rest.repo

import rx.Observable
import training.cg.com.kotlin.database.CountryDB
import training.cg.com.kotlin.model.Country
import training.cg.com.kotlin.rest.service.CountryListService
import training.cg.com.kotlin.util.ConnectionObserver
import javax.inject.Inject

class CountryRepository @Inject constructor(
        private val countryListService: CountryListService,
        private val countryDB: CountryDB,
        connectionObserver: ConnectionObserver): CacheRepository<List<Country>>(connectionObserver) {

    private var memory: List<Country>? = null

    override val memoryCall: Observable<List<Country>>
        get() = Observable.just(memory)

    override val cacheCall: Observable<List<Country>>
        get() = Observable.just(countryDB.get())

    override val restCall: Observable<List<Country>> get() = countryListService.getCountries()

    override fun serialize(response: List<Country>) {
        countryDB.put(response)
    }

    override fun keepMemory(response: List<Country>?) {
        memory = response
    }
}