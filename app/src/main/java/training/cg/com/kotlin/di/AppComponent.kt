package training.cg.com.kotlin.di

import dagger.Component
import training.cg.com.kotlin.nations.CountryListFragment
import training.cg.com.kotlin.main.HomeActivity
import javax.inject.Singleton

@Singleton @Component(modules = arrayOf(AppModule::class, RestModule::class, UtilityModule::class))
interface AppComponent {

    fun inject(homeActivity: HomeActivity)
    fun inject(countryListFragment: CountryListFragment)
}