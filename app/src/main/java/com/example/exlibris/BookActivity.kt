package com.example.exlibris

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.exlibris.data.Book
import com.example.exlibris.db.BookDao

class BookActivity : AppCompatActivity() {

    private lateinit var tvBookName : TextView
    private lateinit var tvAuthor : TextView
    private lateinit var tvPublishingHouse : TextView
    private lateinit var tvISBN : TextView
    private lateinit var ivBook : ImageView
    private lateinit var cbRead : CheckBox
    private lateinit var btnGoHome: Button
    private lateinit var btnEdit: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)


        /*
        val titulo = bundle?.getString("titulo")
        val autor = bundle?.getString("autor")
        val imagen = bundle?.getString("imagen")
        val editorial = bundle?.getString("editorial")
        val isbn = bundle?.getString("ISBN")
        val leido = bundle?.getBoolean("leido")
        val id = bundle?.getInt("id")
        */

        setupUI()
    }

    private fun setupUI() {
        val bundle = intent.extras
        var book: Book? = intent.getParcelableExtra("BOOK")

        tvBookName = findViewById(R.id.tvBookName)
        tvAuthor = findViewById(R.id.tvAuthor)
        ivBook = findViewById(R.id.ivBook)
        tvPublishingHouse = findViewById(R.id.tvPublishingHouse)
        tvISBN = findViewById(R.id.tvISBN)
        cbRead = findViewById(R.id.cbRead)
        btnGoHome = findViewById(R.id.btnGoHome)
        btnEdit = findViewById(R.id.btnEdit)
        btnDelete = findViewById(R.id.btnDelete)

        ivBook.setImageBitmap(BitmapFactory.decodeFile(book?.resImage.toString()))
        cbRead.isChecked = book?.read ?: false
        tvBookName.text = book?.name.toString()
        tvAuthor.text = book?.author.toString()
        tvPublishingHouse.text = book?.publishingHouse.toString()
        tvISBN.text = book?.isbn.toString()

        btnGoHome.setOnClickListener {
            if (book != null) {
                goHome()
            }
        }
        btnEdit.setOnClickListener { editBook() }
        btnDelete.setOnClickListener {
            if (book != null) {
                deleteBook(book)
            }
        }

        cbRead.setOnCheckedChangeListener { cbRead, isChecked ->
            if (book != null && isChecked != book.read) {
                book.read = cbRead.isChecked()
                saveBook(book)
            }
        }
    }

    private fun saveBook(book: Book) {
        BookDao(this@BookActivity.applicationContext).updateBook(book)
    }

    private fun deleteBook(book: Book) {
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle(book.name)
            .setMessage("¿Desea elimnarlo?")
            .setNegativeButton("CANCELAR", { _, _ ->
                    //showMessage("Accion cancelada")
                })
            .setPositiveButton("ELIMINAR", {_,_ ->
                    BookDao(this@BookActivity.applicationContext).deleteBook(book)
                    showMessage("Libro eliminado exitosamente")
                    finish()
                })
            .setCancelable(false)
            .show()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun editBook() {
        TODO("Not yet implemented")
    }

    private fun goHome() {
        finish()
    }





}