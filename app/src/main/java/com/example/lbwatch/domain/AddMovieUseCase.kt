package com.example.lbwatch.domain

import android.content.Context
import com.example.lbwatch.dataLayer.model.Movie
import com.example.lbwatch.dataLayer.model.MovieDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddMovieUseCase(context: Context) {

    private val movieDao = MovieDB.getDb(context).getDao()

    suspend fun execute(title: String, releaseDate: String, posterPath: String) {
        val movie = Movie(
            null,
            title,
            releaseDate,
            posterPath
        )
        withContext(Dispatchers.IO) {
            movieDao.insert(movie)
        }
    }
}