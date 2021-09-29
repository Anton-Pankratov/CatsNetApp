package net.app.catsnetapp.di

import android.content.Context
import coil.Coil
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import net.app.catsnetapp.BuildConfig
import net.app.catsnetapp.network.CatsOkHttpClient
import net.app.catsnetapp.network.CatsRetrofitService
import net.app.catsnetapp.repository.CatsNetRepository
import net.app.catsnetapp.ui.list.CatsAdapter
import net.app.catsnetapp.ui.list.CatsDiffCallback
import net.app.catsnetapp.ui.main.MainViewModel
import net.app.catsnetapp.utils.API_KEY
import net.app.catsnetapp.utils.BASE_URL
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single(named("context")) { get<Context>() }

    single(named(BASE_URL)) { BuildConfig.BASE_URL }
    single(named(API_KEY)) { BuildConfig.API_KEY }

    // Network
    single { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }
    factory { CatsOkHttpClient(get(named(API_KEY))).build() }
    factory { CatsRetrofitService(get(named(BASE_URL)), get(), get()).build() }

    // Repository
    factory { CatsNetRepository(get()) }

    // Ui
    viewModel { MainViewModel(get()) }

    single { CatsDiffCallback() }
    factory(named("ImageLoader")) { Coil.imageLoader(get(named("context"))) }
}