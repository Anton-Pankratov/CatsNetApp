package net.app.catsnetapp.ui.list

import android.content.Context
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.transform.RoundedCornersTransformation
import net.app.catsnetapp.models.Cat
import net.app.catsnetapp.ui.main.MainViewModel
import net.app.catsnetapp.utils.toDp

const val IMAGE_SIZE = 140
const val IMAGE_MARGIN = 8
const val CORNERS_SIZE = 16f

class CatViewHolder(private val catView: AppCompatImageView) :
    RecyclerView.ViewHolder(catView) {

    var cat: Cat? = null
        private set

    fun onBind(
        cat: Cat, listener: OnCatItemViewClickListener?,
        viewModel: MainViewModel
    ) {
        this.cat = cat
        with(viewModel) {
            catView.apply {
                load(cat.url, imageLoader) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(CORNERS_SIZE))
                }
                setOnClickListener { listener?.onClick(cat) }
            }
        }
    }

    private fun ImageView.setCatImage() {
        // https://stackoverflow.com/questions/13402782/show-dialogfragment-with-animation-growing-from-a-point
    }

    companion object {
        fun create(context: Context) =
            CatImageView(context).let(::CatViewHolder)

        private fun CatImageView(context: Context): AppCompatImageView {
            context.apply {
                return AppCompatImageView(this)
                    .apply { setViewParams() }
            }
        }

        private fun AppCompatImageView.setViewParams() {
            layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                context.toDp(IMAGE_SIZE)).apply { setMargins(context) }
        }

        private fun RecyclerView.LayoutParams.setMargins(context: Context) {
            context.toDp(IMAGE_MARGIN).apply {
                setMargins(this, this, this, this)
            }
        }
    }
}