package net.app.catsnetapp.ui.list

import android.content.Context
import android.os.Build
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import coil.transform.RoundedCornersTransformation
import coil.util.CoilUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.app.catsnetapp.models.Cat
import net.app.catsnetapp.utils.*
import okhttp3.OkHttpClient
import org.koin.core.component.KoinComponent

class CatViewHolder(private val catView: AppCompatImageView) :
    RecyclerView.ViewHolder(catView), KoinComponent {

    var cat: Cat? = null
        private set

    suspend fun onBind(
        cat: Cat, listener: OnCatItemViewClickListener?,
        imageLoader: ImageLoader
    ) {
        this.cat = cat
        catView.apply {
            setCatImage(imageLoader, listener)
        }
    }

    private suspend fun ImageView.setCatImage(
        imageLoader: ImageLoader,
        listener: OnCatItemViewClickListener?
    ) {
        withContext(Dispatchers.Main) {
            setImage(cat?.url, imageLoader)
            setOnCatClickListener(listener)
        }
    }

    private fun ImageView.setOnCatClickListener(
        listener: OnCatItemViewClickListener?
    ) {
        setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                cat?.let {
                    listener?.onClick(it)
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