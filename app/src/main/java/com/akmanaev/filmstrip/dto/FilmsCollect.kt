package com.akmanaev.filmstrip.dto

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "data")
data class FilmsCollect(
    @Element(name = "post")
    val films: MutableList<FilmDetails> = mutableListOf<FilmDetails>()
)