package com.akmanaev.filmstrip.io

import com.akmanaev.filmstrip.dto.FilmContent
import com.akmanaev.filmstrip.dto.FilmDetails
import com.akmanaev.filmstrip.dto.FilmsCollect
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val apiService: ApiService) {

    private val films = mutableListOf<FilmDetails>()

    private val filmsContent = mutableMapOf<String, FilmContent>()

    private val filmsDetails = mutableMapOf<String, FilmDetails>()

    private suspend fun readFilmsListIfNeed(){
        if (films.isEmpty()) {
            apiService.getFilmsForAndroidClient().body()?.let {
                films.addAll(it.films)
            }
        }
    }
    suspend fun getFilmsList(): FilmsCollect {
        readFilmsListIfNeed()
        return FilmsCollect(films)
    }

    private suspend fun readFilmContentIfNeed(filmId: String): FilmContent?{
        if (filmsContent.containsKey(filmId))
            return filmsContent[filmId]
        apiService.getFilmContent(filmId).body()?.let {
            filmsContent[filmId] = it
            return it
        }
        return null
    }
    suspend fun getFilmContent(filmId: String) = readFilmContentIfNeed(filmId)

    private suspend fun readFilmDetailsIfNeed(filmId: String): FilmDetails?{
        if (filmsDetails.isEmpty()) {
            readFilmsListIfNeed()
            for (film in films) {
                filmsDetails[film.id] = film
            }
        }
        if (filmsDetails.containsKey(filmId))
            return filmsDetails[filmId]
        return null
    }
    suspend fun getFilmDetails(filmId: String) = readFilmDetailsIfNeed(filmId)
}
