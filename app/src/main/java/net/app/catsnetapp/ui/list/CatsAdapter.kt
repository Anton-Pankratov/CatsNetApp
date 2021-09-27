package net.app.catsnetapp.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import net.app.catsnetapp.models.Cat

class CatsAdapter(diffCallback: CatsDiffCallback) :
    ListAdapter<Cat, CatViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}