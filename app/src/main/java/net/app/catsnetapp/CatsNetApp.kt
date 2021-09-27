package net.app.catsnetapp

import androidx.multidex.MultiDexApplication
import net.app.catsnetapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CatsNetApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@CatsNetApp)
            modules(appModule)
        }
    }
}