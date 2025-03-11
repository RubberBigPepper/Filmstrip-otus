package com.akmanaev.filmstrip.expandablerecyclerview

import com.akmanaev.filmstrip.dto.FilmDetails

data class ParentData(
    val parentTitle: String? = null,
    var type: Int = Constants.PARENT,
    var subList: MutableList<FilmDetails> = ArrayList(),
    var isExpanded: Boolean = false
)