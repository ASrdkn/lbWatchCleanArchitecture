package com.example.lbwatch.domain

import com.example.lbwatch.dataLayer.model.Item

class SearchUseCase(private val movieRepository: MovieRepository) {

    suspend fun execute(apiKey: String, query: String): List<Item>? {
        return movieRepository.fetchMovies(apiKey, query)
    }
}