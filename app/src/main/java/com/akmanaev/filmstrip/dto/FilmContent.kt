package com.akmanaev.filmstrip.dto

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.TextContent
import com.tickaroo.tikxml.annotation.Xml


@Xml(name = "data")
data class FilmContent(
    @PropertyElement(name = "title")
    val title: String = "",

    @PropertyElement(name = "url")
    val url: String = "",

    @Path("imgs")
    @Element(name = "img")
    val frames: MutableList<FrameImage> = mutableListOf(),

    @Path("imgs")
    @Element(name = "mp3")
    val sounds: MutableList<FrameSound> = mutableListOf(),
)

@Xml
data class FrameImage(
    @TextContent
    var imageUrl: String = "",
)

@Xml
data class FrameSound(
    @TextContent
    var mp3Url: String = "",
)