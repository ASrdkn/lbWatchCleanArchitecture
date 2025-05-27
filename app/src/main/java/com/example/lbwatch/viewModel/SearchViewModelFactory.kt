package com.example.lbwatch.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lbwatch.domain.SearchUseCase
import com.example.lbwatch.dataLayer.repository.MovieRepositoryImpl
import com.example.lbwatch.dataLayer.api.ClientAPI
import com.example.lbwatch.dataLayer.model.MovieDB

class SearchViewModelFactory(
    private val application: Application,
    private val searchUseCase: SearchUseCase? = null // searchUseCase может быть передан извне
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            // Если searchUseCase не был передан в фабрику, создаем его внутри
            val useCase = searchUseCase ?: run {
                val movieDb = MovieDB.getDb(application)
                val dao = movieDb.getDao()
                val repository = MovieRepositoryImpl(ClientAPI.create(), dao)

                SearchUseCase(repository)
            }

            // Создаем и возвращаем ViewModel
            SearchViewModel(application, useCase) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}


