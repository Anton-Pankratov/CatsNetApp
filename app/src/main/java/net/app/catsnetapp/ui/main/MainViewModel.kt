package net.app.catsnetapp.ui.main

import android.content.ContentResolver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.app.catsnetapp.data.network.Failure
import net.app.catsnetapp.data.network.Success
import net.app.catsnetapp.data.storage.SaveImageState
import net.app.catsnetapp.models.Cat
import net.app.catsnetapp.models.StoredCatImage
import net.app.catsnetapp.repository.CatsNetRepository
import net.app.catsnetapp.ui.base.BaseViewModel
import net.app.catsnetapp.ui.list.CatsDiffCallback
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

class MainViewModel(private val repository: CatsNetRepository) : BaseViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    val diffCallback: CatsDiffCallback by inject()

    private val _catsFlow = MutableStateFlow<List<Cat>>(emptyList())
    val catsFlow: StateFlow<List<Cat>> = _catsFlow

    val saveStateEvent: LiveData<SaveImageState>
        get() = repository.saveState

    private val savedCats = mutableListOf<Cat>()

    init {
        fetchCatsImages()
    }

    fun saveCatImage(contentResolver: ContentResolver?, image: StoredCatImage) {
        repository.saveCatImageInGallery(contentResolver, image)
    }

    fun fetchCatsImages() {
        viewModelScope.launch {
            repository.fetchCatsImages().apply {
                when (this) {
                    is Success -> {
                        data?.let { cats ->
                            savedCats.apply {
                                addAll(cats)
                                _catsFlow.tryEmit(toList())
                            }
                        }
                    }
                    is Failure -> {

                    }
                }
            }
        }
    }
}