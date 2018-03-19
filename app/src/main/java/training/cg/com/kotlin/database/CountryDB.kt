package training.cg.com.kotlin.database

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import training.cg.com.kotlin.extension.put
import training.cg.com.kotlin.model.Country
import javax.inject.Inject

class CountryDB @Inject constructor(context: Context) {

    private val sharedPrefs: SharedPreferences by lazy { context.getSharedPreferences("CountriesDB", Context.MODE_PRIVATE) }

    private val gson = GsonBuilder().setPrettyPrinting().create()

    private val KEY = "COUNTRIES_KEY"

    fun put(countries: List<Country>) {
        sharedPrefs.put { putString(KEY, gson.toJson(countries).toString()) }
    }

    fun get(): List<Country>? {
        return sharedPrefs.getString(KEY, null)?.let {
            gson.fromJson(it, object : TypeToken<List<Country>>(){}.type)
        }
    }
}