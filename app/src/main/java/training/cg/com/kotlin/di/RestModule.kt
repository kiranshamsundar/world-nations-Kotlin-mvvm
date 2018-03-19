package training.cg.com.kotlin.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import training.cg.com.kotlin.rest.service.CountryListService
import javax.inject.Singleton

@Singleton @Module
class RestModule {

    @Provides @Singleton fun provideRetrofit(okHttpClient: OkHttpClient,
                                             gson: Gson): Retrofit = Retrofit.Builder()
//            .baseUrl("https://api.github.com")
            .baseUrl("https://restcountries.eu/rest/v2/")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides @Singleton fun provideGson(): Gson = GsonBuilder()
            .setPrettyPrinting()
            .create()

    @Provides @Singleton fun provideHttpClient(loggingInterceptor: Interceptor): OkHttpClient = OkHttpClient.Builder()
            .followRedirects(false)
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides @Singleton fun provideHttpLoggingInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }


    @Provides @Singleton fun provideCountryListService(retrofit: Retrofit): CountryListService = retrofit.create(CountryListService::class.java)


}