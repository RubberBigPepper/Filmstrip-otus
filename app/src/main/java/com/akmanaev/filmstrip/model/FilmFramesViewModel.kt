package com.akmanaev.filmstrip.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.akmanaev.filmstrip.dto.FilmContent
import com.akmanaev.filmstrip.io.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FilmFramesViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var filmDetails: FilmContent? = null

    private var currentFrame: Int = 0

    private var _curFrame = MutableLiveData<OneFrame>()

    private fun updateCurFrame() {
        if (filmDetails == null)
            return
        val frame = filmDetails?.frames!![currentFrame]
        val mp3 = filmDetails?.sounds!![currentFrame]
        _curFrame.value = OneFrame(frame.imageUrl, mp3.mp3Url)
    }

    val curFrame: LiveData<OneFrame> = _curFrame

    fun nextFrame() {
        if (filmDetails == null || currentFrame >= filmDetails?.frames?.lastIndex!!)
            return
        currentFrame++
        updateCurFrame()
    }

    fun prevFrame() {
        if (filmDetails == null || currentFrame <= 0)
            return
        currentFrame--
        updateCurFrame()
    }

    fun fetchData(filmId: String) = liveData {
        emit(NetworkResultState.Loading)
        try {
            val data = repository.getFilmDetails(filmId)
            data.body()?.let{
                if (filmDetails == null) {
                    filmDetails = it
                    currentFrame = 0
                    updateCurFrame()
                }
            }
            emit(NetworkResultState.Success(data = filmId))
        } catch (ex: Exception) {
            emit(NetworkResultState.Error(error = ex))
        }
    }
}