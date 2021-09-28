package net.app.catsnetapp.ui.list

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import net.app.catsnetapp.models.Cat

class CatViewHolder(private val catView: AppCompatImageView) :
    RecyclerView.ViewHolder(catView) {

    var cat: Cat? = null
        private set

    fun onBind(
        cat: Cat, listener: OnCatItemViewClickListener?,
        imageLoader: ImageLoader
    ) {
        this.cat = cat
        catView.apply {
            load(cat.url, imageLoader)
            setOnClickListener { listener?.onClick(cat) }
        }
    }

    companion object {
        fun create(context: Context) =
            AppCompatImageView(context).let(::CatViewHolder)
    }
}