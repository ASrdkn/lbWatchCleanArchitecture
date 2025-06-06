package com.example.lbwatch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lbwatch.R
import com.example.lbwatch.dataLayer.model.Item
import com.squareup.picasso.Picasso

class SearchAdapter(
    private val context: Context,
    private val listener: RecyclerItemListener
) : RecyclerView.Adapter<SearchAdapter.SearchHolder>() {

    private var list: List<Item> = emptyList()

    // Обновление данных в адаптере
    fun updateData(newList: List<Item>) {
        list = newList
        notifyDataSetChanged() // Обновляем адаптер с новыми данными
    }

    // Создание нового ViewHolder для каждого элемента
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie_detal, parent, false)
        val viewHolder = SearchHolder(view)

        view.setOnClickListener { v -> listener.onItemClick(v, viewHolder.adapterPosition) }

        return viewHolder
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val item = list[position]
        holder.titleTextView.text = item.title
        holder.releaseDateTextView.text = item.releaseDate
        holder.overviewTextView.text = item.overview

        // Если у элемента есть изображение, загружаем его с помощью Picasso
        if (item.posterPath != "N/A") {
            Picasso.get().load(item.posterPath).into(holder.imageView)
        }
    }

    // Возвращаем количество элементов в списке
    override fun getItemCount(): Int {
        return list.size
    }

    // Метод для получения элемента по позиции
    fun getItemAtPosition(pos: Int): Item {
        return list[pos]
    }

    inner class SearchHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleTextView: TextView = view.findViewById(R.id.title_textview)
        var overviewTextView: TextView = view.findViewById(R.id.overview_overview)
        var releaseDateTextView: TextView = view.findViewById(R.id.release_date_textview)
        var imageView: ImageView = view.findViewById(R.id.movie_imageview)

        init {
            view.setOnClickListener { v: View ->
                listener.onItemClick(v, adapterPosition)
            }
        }
    }

    interface RecyclerItemListener {
        fun onItemClick(v: View, position: Int)
    }
}