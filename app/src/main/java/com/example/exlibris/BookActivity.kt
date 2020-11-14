package com.example.exlibris

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.example.exlibris.db.BookDao
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
    private lateinit var btnBack: Button
    private lateinit var btnEdit: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        val bundle = intent.extras
        val titulo = bundle?.getString("titulo")
        val autor = bundle?.getString("autor")
        val imagen = bundle?.getString("imagen")
        val editorial = bundle?.getString("editorial")
        val isbn = bundle?.getString("ISBN")
        val leido = bundle?.getBoolean("leido")
        val id = bundle?.getInt("id")


        setupUI()

        tvTituloLibro.text = titulo.toString()
        tvNombreAutor.text = autor.toString()
        ivBook.setImageBitmap(BitmapFactory.decodeFile(imagen))
        tvEditorial.text = editorial.toString()
        tvISBN.text = isbn.toString()

    }

    private fun setupUI() {
        tvTituloLibro = findViewById(R.id.tvTituloLibro)
        tvNombreAutor = findViewById(R.id.tvNombreAutor)
        ivBook = findViewById(R.id.ivBook)
        tvEditorial = findViewById(R.id.tvEditorial)
        tvISBN = findViewById(R.id.tvISBN)
        cbLeido = findViewById(R.id.cbLeido)
        cbLeido.isChecked = false
        btnBack = findViewById(R.id.btnBack)
        btnEdit = findViewById(R.id.btnEdit)
        btnDelete = findViewById(R.id.btnDelete)

        btnBack.setOnClickListener { backAndSave() }
        btnEdit.setOnClickListener { editBook() }
        btnDelete.setOnClickListener { deleteBook() }
    }

    private fun deleteBook() {
        finish()
    }

    private fun editBook() {
        TODO("Not yet implemented")
    }

    private fun backAndSave() {
        finish()
    }





}