package net.app.catsnetapp.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import net.app.catsnetapp.models.Cat

class CatsAdapter(
    diffCallback: CatsDiffCallback,
    private val glide: RequestManager
) : ListAdapter<Cat, CatViewHolder>(diffCallback) {

    private val adapterScope = CoroutineScope(Dispatchers.Main)

    private var onCatClickListener: OnCatItemViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder.create(parent.context)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        adapterScope.async {
            holder.onBind(getItem(position), onCatClickListener, glide)
        }.onJoin
    }

    fun setOnCatClickListener(listener: OnCatItemViewClickListener) {
        onCatClickListener = listener
    }
}