package net.app.catsnetapp.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker
import net.app.catsnetapp.BuildConfig
import net.app.catsnetapp.data.network.CatsOkHttpClient
import net.app.catsnetapp.data.network.CatsRetrofitService
import net.app.catsnetapp.data.storage.CatImageSaver
import net.app.catsnetapp.data.storage.CatImageSaverImpl
import net.app.catsnetapp.repository.CatsNetRepository
import net.app.catsnetapp.repository.ErrorsHandler
import net.app.catsnetapp.ui.cat.CatViewModel
import net.app.catsnetapp.ui.list.CatsDiffCallback
import net.app.catsnetapp.ui.main.MainViewModel
import net.app.catsnetapp.utils.*
import net.app.catsnetapp.utils.permission.StorageAccessPermission
import net.app.catsnetapp.utils.permission.StorageAccessPermissionImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single(named(DI_CONTEXT)) { get<Context>() }

    single(named(BASE_URL)) { BuildConfig.BASE_URL }
    single(named(API_KEY)) { BuildConfig.API_KEY }

    // Network
    single { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }
    factory { CatsOkHttpClient(get(named(API_KEY))).build() }
    factory { CatsRetrofitService(get(named(BASE_URL)), get(), get()).build() }
    single { ErrorsHandler }
    single { InternetAvailabilityChecker.getInstance() }

    // Repository
    factory { CatsNetRepository(get(), get()) }

    // Ui
    viewModel { MainViewModel(get()) }
    viewModel { CatViewModel(get()) }

    single { CatsDiffCallback() }

    single(named(DI_GLIDE)) { Glide.with(androidContext()) }

    // Storage

    single<StorageAccessPermission> { (activity: AppCompatActivity) ->
        StorageAccessPermissionImpl(activity)
    }

    single<CatImageSaver> { CatImageSaverImpl() }
}