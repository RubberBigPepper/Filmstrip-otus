package com.akmanaev.filmstrip.io

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val apiService: ApiService) {
    suspend fun getFilmsList() = apiService.getFilmsForAndroidClient()

    suspend fun getFilmDetails(filmId: String) = apiService.getFilmDetails(filmId)
}
