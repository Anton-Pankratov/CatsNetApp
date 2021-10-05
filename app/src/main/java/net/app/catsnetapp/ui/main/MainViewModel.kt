package net.app.catsnetapp.ui.main

import android.content.ContentResolver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.app.catsnetapp.models.Cat
import net.app.catsnetapp.data.network.Failure
import net.app.catsnetapp.data.network.Success
import net.app.catsnetapp.data.storage.SaveImageState
import net.app.catsnetapp.models.StoredCatImage
import net.app.catsnetapp.repository.CatsNetRepository
import net.app.catsnetapp.ui.base.BaseViewModel
import kotlin.coroutines.CoroutineContext

class MainViewModel(private val repository: CatsNetRepository) : BaseViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    private val _cats = MutableLiveData<List<Cat>>()
    val cats: LiveData<List<Cat>> = _cats

    val saveStateEvent: StateFlow<SaveImageState>
        get() = repository.saveState

    init {
        fetchCatsImages()
    }

    fun saveCatImage(contentResolver: ContentResolver?, image: StoredCatImage) {
        repository.saveCatImageInGallery(contentResolver, image)
    }

    private fun fetchCatsImages() {
        viewModelScope.launch {
            repository.fetchCatsImages().apply {
                when (this) {
                    is Success -> {
                        data?.let { _cats.postValue(it) }
                    }
                    is Failure -> {

                    }
                }
            }
        }
    }

}