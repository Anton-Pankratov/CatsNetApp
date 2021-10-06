package net.app.catsnetapp.ui.main.view

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import net.app.catsnetapp.models.Cat
import net.app.catsnetapp.ui.list.CatsAdapter
import net.app.catsnetapp.ui.list.CatsDiffCallback
import net.app.catsnetapp.utils.DI_COIL_IMAGE_LOADER
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class CatsView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    RecyclerView(context, attrs), KoinComponent {

    private val diffCallback: CatsDiffCallback by inject()
    private val imageLoader: ImageLoader by inject(named(DI_COIL_IMAGE_LOADER))

    private var onCatViewCatEvent: CatsViewClickEvent? = null

    private val catsAdapter: CatsAdapter by lazy {
        CatsAdapter(diffCallback, imageLoader).apply {
            setOnCatClickListener { url ->
                onCatViewCatEvent?.onCatClick(url)
            }
        }
    }

    init {
        layoutManager = GridLayoutManager(context, 3)
        adapter = catsAdapter
    }

    fun setCatClickEventListener(listener: CatsViewClickEvent) {
        onCatViewCatEvent = listener
    }

    fun LifecycleOwner.observeCats(cats: LiveData<List<Cat>>) {
        cats.observe(this) {
            catsAdapter.submitList(it)
        }
    }
}