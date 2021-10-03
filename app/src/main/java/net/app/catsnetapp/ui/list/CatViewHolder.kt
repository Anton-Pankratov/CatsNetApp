package net.app.catsnetapp.ui.list

import android.content.Context
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import net.app.catsnetapp.models.Cat
import net.app.catsnetapp.utils.*
import org.koin.core.component.KoinComponent
import kotlin.coroutines.coroutineContext

class CatViewHolder(private val catView: AppCompatImageView) :
    RecyclerView.ViewHolder(catView), KoinComponent {

    var cat: Cat? = null
        private set

    fun onBind(
        cat: Cat, listener: OnCatItemViewClickListener?,
        imageLoader: ImageLoader
    ) {
        this.cat = cat
        catView.setCatImage(listener, imageLoader)
    }

    private fun ImageView.setCatImage(
        listener: OnCatItemViewClickListener?,
        imageLoader: ImageLoader
    ) {
        setCatImage(cat?.url, imageLoader)

        setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                cat?.let {
                    listener?.onClick(it.url)
                }
            }
        }
    }

    companion object {
        fun create(context: Context) =
            createView(context).let(::CatViewHolder)

        private fun createView(context: Context): AppCompatImageView {
            context.apply {
                return AppCompatImageView(this)
                    .apply { setViewParams() }
            }
        }

        private fun AppCompatImageView.setViewParams() {
            layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                context.toDp(IMAGE_SIZE)
            ).apply { setMargins(context) }
        }

        private fun RecyclerView.LayoutParams.setMargins(context: Context) {
            context.toDp(IMAGE_MARGIN).apply {
                setMargins(this, this, this, this)
            }
        }
    }
}