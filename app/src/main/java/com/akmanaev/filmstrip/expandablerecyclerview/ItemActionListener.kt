package com.akmanaev.filmstrip.expandablerecyclerview

interface ItemActionListener {
    fun onItemClicked(filmId: String)
    fun onItemLongClicked(filmId: String)
}