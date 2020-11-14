package com.example.exlibris.adapter

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.exlibris.AddBookActivity
import com.example.exlibris.BookActivity
import com.example.exlibris.R
import com.example.exlibris.data.Book
import com.example.exlibris.preferences.PreferenceActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.view.*
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
            view.tvAutor.text = books.author
            view.ivBook.setImageBitmap(BitmapFactory.decodeFile(books.resImage))
            view.setOnClickListener{
                val intent = Intent(view.context,BookActivity::class.java)
                intent.putExtra("titulo", view.tvTitulo.getText())
                intent.putExtra("autor", view.tvAutor.getText())
                intent.putExtra("imagen", books.resImage)
                intent.putExtra("editorial",books.publishingHouse)
                intent.putExtra("ISBN", books.isbn)
                intent.putExtra("leido",books.read)
                intent.putExtra("id",books.id)
                view.getContext().startActivity(intent)
            }


            /*view.setOnClickListener() {
                val intent = new Intent(view.getContext(), BookActivity.class);
                // envías los TextView de esta forma:
                intent.putExtra("clave1", textViewNombre.getText());
                // para las imágenes de esta forma:
                intent.putExtra("clave2", shows.get(getAdapterPosition()).getImg_show());
                view.getContext().startActivity(intent);
            }*/
        }


    }




}
