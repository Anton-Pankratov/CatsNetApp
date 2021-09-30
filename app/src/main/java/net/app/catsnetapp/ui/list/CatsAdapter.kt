package net.app.catsnetapp.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil.ImageLoader
import net.app.catsnetapp.models.Cat
import net.app.catsnetapp.ui.main.MainViewModel

class CatsAdapter(private val viewModel: MainViewModel) :
    ListAdapter<Cat, CatViewHolder>(viewModel.catsDiffCallback) {

    private var onCatClickListener: OnCatItemViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder.create(parent.context)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.onBind(getItem(position), onCatClickListener, viewModel)
    }

    fun setOnCatClickListener(listener: OnCatItemViewClickListener) {
        onCatClickListener = listener
    }
}