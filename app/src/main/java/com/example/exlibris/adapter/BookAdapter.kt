package com.example.exlibris.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exlibris.R
import com.example.exlibris.data.Book
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_books.view.*

class BookAdapter(val books : List<Book>):RecyclerView.Adapter<BookAdapter.BookHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BookHolder(layoutInflater.inflate(R.layout.item_books,parent,false))
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.render(books[position])
    }

    override fun getItemCount(): Int = books.size

    class BookHolder(val view:View):RecyclerView.ViewHolder(view) {
        fun render (books:Book){
            view.tvTitulo.text = books.name
            view.tvAutor.text = books.nameAuthor
            Picasso.get().load(books.resImage).into(view.ivBook)
        }

    }

}
