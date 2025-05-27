package com.example.lbwatch.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lbwatch.dataLayer.model.Movie
import com.example.lbwatch.domain.AddMoviesUseCase
import com.example.lbwatch.domain.DeleteMoviesUseCase
import com.example.lbwatch.domain.GetMoviesUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val deleteMoviesUseCase: DeleteMoviesUseCase,
    private val addMoviesUseCase: AddMoviesUseCase
) : AndroidViewModel(application) {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val selectedMovies = mutableSetOf<Movie>()

    // Загрузка фильмов
    fun loadMovies() {
        viewModelScope.launch {
            _movies.value = getMoviesUseCase.execute()
        }
    }
    // Добавление нового фильма
    fun addMovie(movie: Movie) {
        viewModelScope.launch {
            addMoviesUseCase.execute(movie)
            loadMovies()
        }
    }
    // Удаление выбранных фильмов
    fun deleteMovies() {
        if (selectedMovies.isEmpty()) {
            return
        }
        viewModelScope.launch {
            deleteMoviesUseCase.execute(selectedMovies.toList())
            loadMovies()
        }
    }
    // Управление выбором фильма
    fun toggleMovieSelection(movie: Movie, isSelected: Boolean) {
        if (isSelected) {
            selectedMovies.add(movie)
        } else {
            selectedMovies.remove(movie)
        }
    }
    fun getSelectedMovies(): List<Movie> {
        return selectedMovies.toList()
    }
    fun areMoviesSelected(): Boolean {
        return selectedMovies.isNotEmpty()
    }
}