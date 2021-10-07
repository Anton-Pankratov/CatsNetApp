package net.app.catsnetapp.ui.list

import android.content.Context
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.app.catsnetapp.models.Cat
import net.app.catsnetapp.utils.*
import org.koin.core.component.KoinComponent

class CatViewHolder(private val catView: ImageView) :
    RecyclerView.ViewHolder(catView), KoinComponent {

    var cat: Cat? = null
        private set

    suspend fun onBind(
        cat: Cat, listener: OnCatItemViewClickListener?,
        glide: RequestManager
    ) {
        this.cat = cat
        catView.apply {
            setCatImage(listener, glide)
        }
    }

    private suspend fun ImageView.setCatImage(
        listener: OnCatItemViewClickListener?,
        glide: RequestManager
    ) {
        withContext(Dispatchers.Main) {
            setImage(cat?.url, cat?.ext, glide)
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

        private fun ImageView.setViewParams() {
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