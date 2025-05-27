package com.example.lbwatch.domain

import com.example.lbwatch.dataLayer.repository.MovieRepository
import com.example.lbwatch.dataLayer.model.Movie
import kotlinx.coroutines.flow.first

class GetMoviesUseCase(private val movieRepository: MovieRepository) {
    // Получаем все фильмы как List<Movie> из Flow
    suspend fun execute(): List<Movie> {
        return movieRepository.getAllMovies().first()
    }
}