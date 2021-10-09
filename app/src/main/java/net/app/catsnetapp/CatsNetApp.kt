package net.app.catsnetapp

import android.app.Application
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker
import net.app.catsnetapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CatsNetApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initInternetChecker()
        initKoin()
    }

    private fun initInternetChecker() {
        InternetAvailabilityChecker.init(this)
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@CatsNetApp)
            modules(appModule)
        }
    }
}