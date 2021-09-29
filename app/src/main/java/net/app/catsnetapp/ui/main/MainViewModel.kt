package net.app.catsnetapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.app.catsnetapp.repository.CatsNetRepository
import net.app.catsnetapp.ui.list.CatsDiffCallback
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import kotlin.coroutines.CoroutineContext

class MainViewModel(private val repository: CatsNetRepository) : ViewModel(), KoinComponent,
    CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    val catsDiffCallback: CatsDiffCallback by inject()
    val imageLoader: ImageLoader by inject(named("ImageLoader"))

    fun callTest() {
        viewModelScope.launch {
            repository.fetchCatsImages()
        }
    }

}