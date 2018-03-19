package training.cg.com.kotlin

import android.support.multidex.MultiDexApplication
import timber.log.Timber
import training.cg.com.kotlin.di.*

class MainApplication : MultiDexApplication() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .restModule(RestModule())
                .utilityModule(UtilityModule())
                .build()
    }

    fun getAppComponent() = appComponent
}