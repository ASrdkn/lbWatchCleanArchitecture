package com.example.lbwatch.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lbwatch.domain.AddMovieUseCase

class AddViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Создаем AddMovieUseCase и передаем его в AddViewModel
        val addMovieUseCase = AddMovieUseCase(application)
        return AddViewModel(application, addMovieUseCase) as T
    }
}