package net.app.catsnetapp.ui.cat

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Dialog
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import coil.load
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import net.app.catsnetapp.R
import net.app.catsnetapp.ui.list.CORNERS_SIZE
import org.koin.androidx.viewmodel.ext.android.viewModel

class CatFragment : DialogFragment() {

    private val viewModel: CatViewModel by viewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireActivity()).apply {
            LayoutInflater.from(context).inflate(R.layout.fragment_cat, null).apply {
                setView(this.apply {
                    val catView = findViewById<ImageView>(R.id.cat_image)
                    val request = ImageRequest.Builder(context).apply {
                        data(viewModel.catImage)
                        crossfade(true)
                        transformations(RoundedCornersTransformation(CORNERS_SIZE))
                        target(catView)
                    }.build()

                    viewModel.imageLoader.enqueue(request)
                })
            }
        }.create()
    }

    override fun onStart() {
        super.onStart()
        val scaleDown: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            dialog?.window?.decorView,
            PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
            PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f),
            PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f),
            PropertyValuesHolder.ofFloat("rotationY", 0f, 360f)
        )
        scaleDown.duration = 1000
        scaleDown.start()
    }

    companion object {
        fun create(): CatFragment {
            return CatFragment()
        }
    }
}