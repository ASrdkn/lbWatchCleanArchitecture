package com.example.lbwatch.domain

import com.example.lbwatch.dataLayer.model.Movie
import com.example.lbwatch.dataLayer.repository.MovieRepository

class AddMoviesUseCase(private val movieRepository: MovieRepository) {

    suspend fun execute(movie: Movie) {
        movieRepository.insertMovie(movie)
    }
}