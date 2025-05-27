package com.example.lbwatch.dataLayer.repository

import com.example.lbwatch.dataLayer.model.Movie
import com.example.lbwatch.dataLayer.model.DAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

// Работа с DAO
class MovieRepository(private val movieDao: DAO) {
    suspend fun insertMovie(movie: Movie) {
        withContext(Dispatchers.IO) {
            movieDao.insert(movie)
        }
    }
    fun getAllMovies(): Flow<List<Movie>> {
        return movieDao.getAll()
    }
    suspend fun deleteMovies(movies: List<Movie>) {
        movies.forEach { movie ->
            movieDao.delete(movie)
        }
    }
}