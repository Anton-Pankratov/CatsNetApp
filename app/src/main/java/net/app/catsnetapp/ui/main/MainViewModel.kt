package net.app.catsnetapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.app.catsnetapp.models.Cat
import net.app.catsnetapp.models.requests.ImagesRequest
import net.app.catsnetapp.network.Failure
import net.app.catsnetapp.network.Success
import net.app.catsnetapp.repository.CatsNetRepository
import net.app.catsnetapp.ui.list.CatsDiffCallback
import net.app.catsnetapp.utils.DI_COIL_IMAGE_LOADER
import net.app.catsnetapp.utils.DI_COIL_IMAGE_REQUEST
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import kotlin.coroutines.CoroutineContext

class MainViewModel(private val repository: CatsNetRepository) : ViewModel(), KoinComponent,
    CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    val catsDiffCallback: CatsDiffCallback by inject()

    val imageLoader: ImageLoader by inject(named(DI_COIL_IMAGE_LOADER))
    val imageRequest: ImageRequest by inject(named(DI_COIL_IMAGE_REQUEST))

    private val _cats = MutableStateFlow<List<Cat>>(emptyList())
    val cats: StateFlow<List<Cat>> = _cats

    init {
        fetchCatsImages()
    }

    private fun fetchCatsImages() {
        viewModelScope.launch {
            repository.fetchCatsImages().apply {
                when (this) {
                    is Success -> {
                        data?.let { _cats.tryEmit(it) }
                    }
                    is Failure -> {

                    }
                }
            }
        }
    }
}