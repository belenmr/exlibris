package com.example.exlibris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_book.view.*
import kotlinx.android.synthetic.main.activity_book.*

class BookActivity : AppCompatActivity() {

    private lateinit var tvTituloLibro : TextView
    private lateinit var tvNombreAutor : TextView
    private lateinit var tvEditorial : TextView
    private lateinit var tvISBN : TextView
    private lateinit var ivBook : ImageView
    private lateinit var cbLeido : CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        val bundle = intent.extras
        val titulo = bundle?.getString("titulo")
        val autor = bundle?.getString("autor")
        val imagen = bundle?.getInt("imagen")


        setupUI()

        tvTituloLibro.text = titulo.toString()
        tvNombreAutor.text = autor.toString()
        if (imagen != null) {
            Picasso.get()
                    .load(imagen)
                    .into(ivBook)
        };

    }

    fun setupUI()
    {
        tvTituloLibro = findViewById(R.id.tvTituloLibro)
        tvNombreAutor = findViewById(R.id.tvNombreAutor)
        ivBook = findViewById(R.id.ivBook)
        tvEditorial = findViewById(R.id.tvEditorial)
        tvISBN = findViewById(R.id.tvISBN)
        cbLeido = findViewById(R.id.cbLeido)
    }


}