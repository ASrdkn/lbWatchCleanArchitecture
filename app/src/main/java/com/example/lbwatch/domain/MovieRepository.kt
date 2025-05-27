package com.example.lbwatch.domain

import com.example.lbwatch.dataLayer.model.Item
import com.example.lbwatch.dataLayer.model.Movie
import kotlinx.coroutines.flow.Flow

// Интерфейс репозитоия
interface MovieRepository {
    suspend fun fetchMovies(apiKey: String, query: String): List<Item>?
    suspend fun getAllMovies(): Flow<List<Movie>>
    suspend fun deleteMovies(movies: List<Movie>)
}