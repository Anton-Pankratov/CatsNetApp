package net.app.catsnetapp.ui.main

import android.content.ContentResolver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import net.app.catsnetapp.data.network.Failure
import net.app.catsnetapp.data.network.Success
import net.app.catsnetapp.data.storage.SaveImageState
import net.app.catsnetapp.models.Cat
import net.app.catsnetapp.models.StoredCatImage
import net.app.catsnetapp.repository.CatsNetRepository
import net.app.catsnetapp.ui.base.BaseViewModel
import kotlin.coroutines.CoroutineContext

class MainViewModel(private val repository: CatsNetRepository) : BaseViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    private val _cats = MutableLiveData<List<Cat>>()
    val cats: LiveData<List<Cat>> = _cats

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
        async(coroutineContext) {
            repository.fetchCatsImages().apply {
                when (this) {
                    is Success -> {
                        data?.let { cats ->
                            savedCats.apply {
                                addAll(cats)
                                _cats.postValue(toList())
                            }
                        }
                    }
                    is Failure -> {

                    }
                }
            }
        }.onJoin
    }
}