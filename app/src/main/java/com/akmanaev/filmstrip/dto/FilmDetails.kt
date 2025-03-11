package com.akmanaev.filmstrip.dto

import java.util.Date
import com.akmanaev.filmstrip.convertor.DateConverter
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml


@Xml(name = "post")
data class FilmDetails(
    @PropertyElement(name = "cat")
    val categories: String = "",
    @PropertyElement(name = "id")
    val id: String = "",
    @PropertyElement(name = "url")
    val url: String = "",
    @PropertyElement(name = "year")
    val year: String = "",
    @PropertyElement(name = "title")
    val title: String = "",
    @PropertyElement(name = "img")
    val imageUrl: String = "",
    @PropertyElement(name = "studio")
    val studio: String = "",
    @PropertyElement(name = "newsdate", converter = DateConverter::class)
    val adding: Date = Date()
)
