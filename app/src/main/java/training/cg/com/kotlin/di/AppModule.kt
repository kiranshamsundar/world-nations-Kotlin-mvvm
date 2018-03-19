package training.cg.com.kotlin.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton @Module
class AppModule(private val context: Context) {

    @Provides @Singleton fun provideContext() = context
}