package training.cg.com.kotlin.di

import android.content.Context
import training.cg.com.kotlin.util.ConnectionObserver
import training.cg.com.kotlin.util.SimpleConnectionObserver
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton @Module
class UtilityModule {

    @Singleton @Provides fun provideSimpleConnectionObserver(context: Context): ConnectionObserver = SimpleConnectionObserver(context)
}