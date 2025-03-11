package com.akmanaev.filmstrip.convertor

import com.akmanaev.filmstrip.dto.FilmDetails
import com.akmanaev.filmstrip.dto.FilmsCollect
import com.akmanaev.filmstrip.expandablerecyclerview.ParentData

fun convertFilmsCollectToParentData(collect: FilmsCollect): MutableList<ParentData> {
    val result = mutableListOf<ParentData>()
    collect.films?.let {
        val categoriedFilms = mutableMapOf<String, MutableList<FilmDetails>>()
        for (film in it) {
            val cat = film.categories!!
            if (!categoriedFilms.contains(cat))
                categoriedFilms[cat] = mutableListOf<FilmDetails>()
            categoriedFilms[cat]?.add(film)
        }
        for (cat in categoriedFilms) {
            result.add(ParentData(parentTitle = cat.key, subList = cat.value))
        }
    }
    return result
}
