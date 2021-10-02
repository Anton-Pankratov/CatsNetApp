package net.app.catsnetapp.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil.ImageLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.app.catsnetapp.models.Cat

class CatsAdapter(
    private val diffCallback: CatsDiffCallback,
    private val imageLoader: ImageLoader,
    private val coroutineScope: CoroutineScope
) : ListAdapter<Cat, CatViewHolder>(diffCallback) {

    private var onCatClickListener: OnCatItemViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder.create(parent.context)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        coroutineScope.launch {
            holder.onBind(getItem(position), onCatClickListener, imageLoader)
        }
    }

    fun setOnCatClickListener(listener: OnCatItemViewClickListener) {
        onCatClickListener = listener
    }
}