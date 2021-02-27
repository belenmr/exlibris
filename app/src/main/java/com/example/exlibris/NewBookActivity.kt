package com.example.exlibris

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.exlibris.adapter.COMPLETED_BOOK
import com.example.exlibris.data.Book
import com.example.exlibris.db.BookDao

class NewBookActivity : AppCompatActivity() {

    private lateinit var tvBookName : TextView
    private lateinit var tvAuthor : TextView
    private lateinit var tvPublishingHouse : TextView
    private lateinit var tvISBN : TextView
    private lateinit var ivBook : ImageView
    private lateinit var btnGoHome: Button
    private lateinit var btnEdit: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_book)

        setupUI()
    }

    private fun setupUI() {
        val book = intent.extras?.getParcelable<Book>(COMPLETED_BOOK)

        tvBookName = findViewById(R.id.tv_BookName)
        tvAuthor = findViewById(R.id.tv_Author)
        ivBook = findViewById(R.id.iv_Book)
        tvPublishingHouse = findViewById(R.id.tv_PublishingHouse)
        tvISBN = findViewById(R.id.tv_ISBN)
        btnGoHome = findViewById(R.id.btn_home)
        btnEdit = findViewById(R.id.btn_edit)
        btnDelete = findViewById(R.id.btn_delete)

        ivBook.setImageBitmap(BitmapFactory.decodeFile(book?.resImage.toString()))
        tvBookName.text = book?.name.toString()
        tvAuthor.text = book?.author.toString()
        tvPublishingHouse.text = book?.publishingHouse.toString()
        tvISBN.text = book?.isbn.toString()

        btnGoHome.setOnClickListener {
            if (book != null) {
                goHome()
            }
        }
        btnEdit.setOnClickListener {
            if (book != null) {
                editBook(book)
            }
        }
        btnDelete.setOnClickListener {
            if (book != null) {
                deleteBook(book)
            }
        }


    }


    private fun goHome() {
        finish()
    }

    private fun editBook(book: Book) {
        startActivity(
            Intent(this, EditBookActivity::class.java)
                .putExtra(COMPLETED_BOOK,book)
        )
        finish()
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
                BookDao(this@NewBookActivity.applicationContext)
                    .deleteBook(book)
                    .subscribe()
                showMessage("Libro eliminado exitosamente")
                finish()
            })
            .setCancelable(false)
            .show()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}