package com.example.lbwatch.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lbwatch.domain.AddMoviesUseCase
import com.example.lbwatch.domain.DeleteMoviesUseCase
import com.example.lbwatch.domain.GetMoviesUseCase

class MainViewModelFactory(
    private val application: Application,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val deleteMoviesUseCase: DeleteMoviesUseCase,
    private val addMoviesUseCase: AddMoviesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application, getMoviesUseCase, deleteMoviesUseCase, addMoviesUseCase) as T
    }
}