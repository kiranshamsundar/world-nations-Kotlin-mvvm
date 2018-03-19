package training.cg.com.kotlin.rest.service

import retrofit2.http.GET
import rx.Observable
import training.cg.com.kotlin.model.Country

interface CountryListService {

    @GET("all")
    fun getCountries(): Observable<List<Country>>
}