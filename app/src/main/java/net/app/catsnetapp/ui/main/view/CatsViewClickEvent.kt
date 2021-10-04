package net.app.catsnetapp.ui.main.view

import net.app.catsnetapp.models.Cat

fun interface CatsViewClickEvent {

    fun onCatClick(cat: Cat?)
}