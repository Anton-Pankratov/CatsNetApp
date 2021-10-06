package net.app.catsnetapp.di

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.util.CoilUtils
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
import net.app.catsnetapp.utils.API_KEY
import net.app.catsnetapp.utils.BASE_URL
import net.app.catsnetapp.utils.DI_COIL_IMAGE_LOADER
import net.app.catsnetapp.utils.DI_CONTEXT
import net.app.catsnetapp.utils.permission.StorageAccessPermission
import net.app.catsnetapp.utils.permission.StorageAccessPermissionImpl
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.get
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

    // Repository
    factory { CatsNetRepository(get(), get()) }

    // Ui
    viewModel { MainViewModel(get()) }
    viewModel { CatViewModel(get()) }

    single { CatsDiffCallback() }

    single(named(DI_COIL_IMAGE_LOADER)) {
        val context = get(named(DI_CONTEXT)) as Context
        ImageLoader.Builder(context)
            .componentRegistry {
                add(
                    if (Build.VERSION.SDK_INT >= 28)
                        ImageDecoderDecoder(context)
                     else GifDecoder()
                )
            }
            .crossfade(true)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(context))
                    .build()
            }.build()
    }

    // Storage

    single<StorageAccessPermission> { (activity: AppCompatActivity) ->
        StorageAccessPermissionImpl(activity)
    }

    single<CatImageSaver> { CatImageSaverImpl() }
}