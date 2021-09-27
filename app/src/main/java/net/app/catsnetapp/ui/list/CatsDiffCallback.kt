package net.app.catsnetapp.ui.list

import androidx.recyclerview.widget.DiffUtil
import net.app.catsnetapp.models.Cat

class CatsDiffCallback : DiffUtil.ItemCallback<Cat>() {

    override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
        return oldItem == newItem
    }
}