package com.akmanaev.filmstrip.io

class Repository {
    suspend fun getFilmsList() = RetrofitService.retrofitService.getFilmsForAndroidClient()

    suspend fun getFilmDetails(filmId: String) =
        RetrofitService.retrofitService.getFilmDetails(filmId)
}
