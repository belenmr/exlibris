package com.example.exlibris.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exlibris.R
import com.example.exlibris.data.BookResponse

class NewsAdapter(val listener: NewsListener): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var news: List<BookResponse> = emptyList()

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvNewsName: TextView = itemView.findViewById(R.id.tvNewsName)
        val tvNewsAuthor: TextView = itemView.findViewById(R.id.tvNewsAuthor)
        val tvNewsPublishingHouse: TextView = itemView.findViewById(R.id.tvNewsPublishingHouse)
        val tvNewsLink: TextView = itemView.findViewById(R.id.tvNewsLink)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_news,
                parent,
                false
            )

        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.apply {
            tvNewsName.text = news[position].name
            tvNewsAuthor.text = news[position].author
            tvNewsPublishingHouse.text = news[position].publishingHouse
            tvNewsLink.text = "Más info en ${news[position].link}"
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }

    fun updateNews(bookResponse: List<BookResponse>){
        this.news = bookResponse
        notifyDataSetChanged()
    }

}

interface NewsListener {

}