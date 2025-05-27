package com.example.lbwatch.domain

import com.example.lbwatch.dataLayer.model.Movie
import com.example.lbwatch.dataLayer.repository.MovieRepository

class DeleteMoviesUseCase(private val movieRepository: MovieRepository) {

    suspend fun execute(movies: List<Movie>) {
        movieRepository.deleteMovies(movies)
    }
}