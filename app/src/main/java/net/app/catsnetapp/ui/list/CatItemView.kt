package net.app.catsnetapp.ui.list

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import coil.ImageLoader
import coil.load
import coil.transform.CircleCropTransformation
import net.app.catsnetapp.models.Cat

class CatItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    AppCompatImageView(context, attrs) {

    private val cat get() = _cat
    private val imageLoader get() = _imageLoader

    init {
        imageLoader?.let {
            load(cat?.url, it) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }

    companion object {
        var _cat: Cat? = null
        var _imageLoader: ImageLoader? = null

        fun create(context: Context, cat: Cat, imageLoader: ImageLoader): CatItemView {
            _cat = cat
            _imageLoader = imageLoader
            return CatItemView(context)
        }
    }

}