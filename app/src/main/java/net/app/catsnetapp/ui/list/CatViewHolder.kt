package net.app.catsnetapp.ui.list

import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import net.app.catsnetapp.models.Cat

class CatViewHolder(private val item: CatItemView, private val imageLoader: ImageLoader) :
    RecyclerView.ViewHolder(item) {

    var cat: Cat? = null
        private set

    fun onBind(cat: Cat, listener: OnCatItemViewClickListener) {
        this.cat = cat


    }
}