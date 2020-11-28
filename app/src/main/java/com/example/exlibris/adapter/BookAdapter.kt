package com.example.exlibris.adapter

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exlibris.BookActivity
import com.example.exlibris.R
import com.example.exlibris.data.Book
import kotlinx.android.synthetic.main.item_books.view.*

const val COMPLETED_BOOK = "Book"

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
            view.tv_Title.text = books.name
            view.tv_Author.text = books.author
            view.ivBook.setImageBitmap(BitmapFactory.decodeFile(books.resImage))
            view.setOnClickListener{
                val intent = Intent(view.context,BookActivity::class.java)
                intent.putExtra(COMPLETED_BOOK, books)
                view.getContext().startActivity(intent)
            }
        }
    }

}
