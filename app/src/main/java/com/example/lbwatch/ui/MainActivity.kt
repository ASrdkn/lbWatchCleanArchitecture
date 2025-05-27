package com.example.lbwatch.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lbwatch.adapter.MainAdapter
import com.example.lbwatch.R
import com.example.lbwatch.dataLayer.model.MovieDB
import com.example.lbwatch.dataLayer.repository.MovieRepository
import com.example.lbwatch.viewModel.MainViewModel
import com.example.lbwatch.domain.AddMoviesUseCase
import com.example.lbwatch.domain.DeleteMoviesUseCase
import com.example.lbwatch.domain.GetMoviesUseCase
import com.example.lbwatch.viewModel.MainViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainAdapter
    private lateinit var imageEmpty: LinearLayout

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            application,
            GetMoviesUseCase(MovieRepository(MovieDB.getDb(application).getDao())),
            DeleteMoviesUseCase(MovieRepository(MovieDB.getDb(application).getDao())),
            AddMoviesUseCase(MovieRepository(MovieDB.getDb(application).getDao()))
        )
    }

    // Регистрация результата для AddActivity
    private val addActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            // Перезагружаем список фильмов, если фильм был добавлен
            loadMovies()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageEmpty = findViewById(R.id.no_movies_layout)
        val deleteBtn = findViewById<ImageView>(R.id.img_delete)

        recyclerView = findViewById(R.id.movies_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val addBtn = findViewById<FloatingActionButton>(R.id.fab)
        addBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            addActivityResultLauncher.launch(intent)  // Используем результат из AddActivity
        }

        // Наблюдаем за списком фильмов из ViewModel
        mainViewModel.movies.observe(this, Observer { movies ->
            if (movies.isNotEmpty()) {
                imageEmpty.visibility = View.INVISIBLE
                recyclerView.visibility = View.VISIBLE
                adapter = MainAdapter(movies) { movie, isSelected ->
                    mainViewModel.toggleMovieSelection(movie, isSelected) // передаем состояние выбора в ViewModel
                }
                recyclerView.adapter = adapter
            } else {
                recyclerView.visibility = View.INVISIBLE
                imageEmpty.visibility = View.VISIBLE
            }
        })

        // Обработчик удаления выбранных фильмов
        deleteBtn.setOnClickListener {
            if (mainViewModel.areMoviesSelected()) {
                mainViewModel.deleteMovies()
                showToast("Удаление успешно.")
            } else {
                showToast("Выберите хотя бы один фильм.")
            }
        }
    }

    companion object {
        const val ADD_VIEW_ACTIVITY_REQUEST_CODE = 1
    }

    private fun loadMovies() {
        mainViewModel.loadMovies()
    }

    private fun showToast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show()
    }
}