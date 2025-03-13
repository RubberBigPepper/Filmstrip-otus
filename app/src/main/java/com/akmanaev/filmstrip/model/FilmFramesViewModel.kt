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

    private var filmContent: FilmContent? = null

    private var currentFrame: Int = 0

    private var _curFrame = MutableLiveData<OneFrame>()

    private var _frames = MutableLiveData<List<OneFrame>>()

    private fun updateCurFrame() {
        if (filmContent == null)
            return
        val frame = filmContent?.frames!![currentFrame]
        val mp3 = filmContent?.sounds!![currentFrame]
        _curFrame.value = OneFrame(frame.imageUrl, mp3.mp3Url)
    }

    val frames: LiveData<List<OneFrame>> = _frames

    val curFrame: LiveData<OneFrame> = _curFrame

    fun nextFrame() {
        if (filmContent == null || currentFrame >= filmContent?.frames?.lastIndex!!)
            return
        currentFrame++
        updateCurFrame()
    }

    fun prevFrame() {
        if (filmContent == null || currentFrame <= 0)
            return
        currentFrame--
        updateCurFrame()
    }

    fun fetchData(filmId: String) = liveData {
        emit(NetworkResultState.Loading)
        try {
            if (filmContent == null) {
                val data = repository.getFilmContent(filmId)
                data?.let {
                    filmContent = it
                    currentFrame = 0
                    updateCurFrame()
                    val frames = mutableListOf<OneFrame>()
                    for (index in (0..<it.frames.size)) {
                        val oneFrame = OneFrame(
                            it.frames[index].imageUrl,
                            it.sounds[index].mp3Url
                        )
                        frames.add(oneFrame)
                    }
                    _frames.value = frames
                }
            }
            emit(NetworkResultState.Success(data = filmId))
        } catch (ex: Exception) {
            emit(NetworkResultState.Error(error = ex))
        }
    }
}