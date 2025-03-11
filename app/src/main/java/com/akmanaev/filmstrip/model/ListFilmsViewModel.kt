package com.akmanaev.filmstrip.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.akmanaev.filmstrip.convertor.convertFilmsCollectToParentData
import com.akmanaev.filmstrip.expandablerecyclerview.ParentData
import com.akmanaev.filmstrip.io.Repository

class ListFilmsViewModel(private val repository:Repository = Repository()): ViewModel() {
    private var data: MutableList<ParentData>? = null

    fun fetchData() = liveData {
        emit(NetworkResultState.Loading)
        try {
            if (data == null)
                data = convertFilmsCollectToParentData(repository.getFilmsList())
            emit(NetworkResultState.Success(data = data!!))
        } catch (ex: Exception) {
            emit(NetworkResultState.Error(error = ex))
        }
    }
}
