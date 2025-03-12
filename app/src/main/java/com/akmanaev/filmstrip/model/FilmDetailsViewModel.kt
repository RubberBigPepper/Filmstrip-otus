package com.akmanaev.filmstrip.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.akmanaev.filmstrip.io.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilmDetailsViewModel  @Inject constructor(private val repository: Repository) : ViewModel() {

    fun fetchData(filmId: String) = liveData {
        emit(NetworkResultState.Loading)
        try {
            kotlinx.coroutines.delay(2000)
            emit(NetworkResultState.Success(data = repository.getFilmDetails(filmId)))
        } catch (ex: Exception) {
            emit(NetworkResultState.Error(error = ex))
        }
    }
}