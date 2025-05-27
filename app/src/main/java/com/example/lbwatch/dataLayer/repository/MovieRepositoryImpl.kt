package com.example.lbwatch.dataLayer.repository

import com.example.lbwatch.dataLayer.api.ClientAPI
import com.example.lbwatch.dataLayer.model.DAO
import com.example.lbwatch.dataLayer.model.Item
import com.example.lbwatch.dataLayer.model.Movie
import com.example.lbwatch.domain.MovieRepository
import kotlinx.coroutines.flow.Flow

// Работа с API и DAO
class MovieRepositoryImpl(
    private val api: ClientAPI,
    private val dao: DAO
) : MovieRepository {
    // Получаем фильмы с помощью API
    override suspend fun fetchMovies(apiKey: String, query: String): List<Item>? {
        val response = api.fetchResponse(apiKey, query)
        return response.items
    }
    // Получаем все фильмы из локальной базы данных
    override suspend fun getAllMovies(): Flow<List<Movie>> {
        return dao.getAll()
    }
    // Удаление фильмов из базы данных
    override suspend fun deleteMovies(movies: List<Movie>) {
        movies.forEach { dao.delete(it) }
    }
}