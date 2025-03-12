package com.akmanaev.filmstrip.io

import com.akmanaev.filmstrip.dto.FilmContent
import com.akmanaev.filmstrip.dto.FilmsCollect
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("dia-list-androidgz.php")
    suspend fun getFilmsForAndroidClient(): Response<FilmsCollect>

    @GET("dia-android.php")
    suspend fun getFilmContent(@Query(value = "id", encoded = true) id: String): Response<FilmContent>
}