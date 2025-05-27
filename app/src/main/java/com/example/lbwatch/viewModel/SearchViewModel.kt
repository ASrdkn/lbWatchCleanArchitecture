package com.example.lbwatch.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lbwatch.dataLayer.model.Item
import com.example.lbwatch.domain.SearchUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
    application: Application,
    private val searchUseCase: SearchUseCase
) : AndroidViewModel(application) {

    private val _items = MutableLiveData<List<Item>?>()
    val items: LiveData<List<Item>?> = _items

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // Запрос на получение фильмов по запросу
    fun fetchMovies(query: String) {
        _loading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = searchUseCase.execute("c54d9bf7", query)
                if (response.isNullOrEmpty()) {
                    _error.postValue("Нет фильмов по запросу")
                } else {
                    _items.postValue(response)
                }
            } catch (e: Exception) {
                _error.postValue("Ошибка при загрузке данных: ${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }
}