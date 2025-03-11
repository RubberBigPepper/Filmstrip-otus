package com.akmanaev.filmstrip.io

import com.akmanaev.filmstrip.dto.FilmContent
import com.akmanaev.filmstrip.dto.FilmsCollect
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("dia-list-androidgz.php")
    suspend fun getFilmsForAndroidClient(): FilmsCollect

    @GET("dia-android.php")
    suspend fun getFilmDetails(@Query(value = "id", encoded = true) id: String): FilmContent
}