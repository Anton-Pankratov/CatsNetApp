package net.app.catsnetapp.ui.list

import net.app.catsnetapp.models.Cat

interface OnCatItemViewClickListener {

    fun onClick(cat: Cat)
}