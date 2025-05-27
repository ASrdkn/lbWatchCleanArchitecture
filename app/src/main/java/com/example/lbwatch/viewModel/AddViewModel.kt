package com.example.lbwatch.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lbwatch.domain.AddMovieUseCase
import kotlinx.coroutines.launch

class AddViewModel(
    application: Application,
    private val addMovieUseCase: AddMovieUseCase
) : AndroidViewModel(application) {

    // LiveData для отслеживания состояния UI
    val title = MutableLiveData<String>()
    val releaseDate = MutableLiveData<String>()
    val moviePosterPath = MutableLiveData<String>()

    // Функция для добавления фильма в базу данных через UseCase
    fun addMovie(title: String, releaseDate: String, posterPath: String) {
        viewModelScope.launch {
            addMovieUseCase.execute(title, releaseDate, posterPath)
        }
    }
}