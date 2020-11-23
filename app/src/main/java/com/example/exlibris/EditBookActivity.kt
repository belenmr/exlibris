package com.example.exlibris

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.exlibris.data.Book
import com.example.exlibris.db.BookDao
import com.google.android.material.textfield.TextInputEditText

class EditBookActivity : AppCompatActivity() {

    private lateinit var imgBook: ImageView
    private lateinit var etName: EditText
    private lateinit var etAuthor: TextInputEditText
    private lateinit var etPublishingHouse: TextInputEditText
    private lateinit var etISBN: TextInputEditText
    private lateinit var btnCancel: Button
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_book)

        setupUI()
    }

    private fun setupUI() {
        var book: Book? = intent.getParcelableExtra("BOOK")

        imgBook = findViewById(R.id.imgBook)
        etName = findViewById(R.id.etName)
        etAuthor = findViewById(R.id.etAuthor)
        etPublishingHouse = findViewById(R.id.etPublishingHouse)
        etISBN = findViewById(R.id.etISBN)
        btnCancel = findViewById(R.id.btnCancel)
        btnSave = findViewById(R.id.btnSave)

        imgBook.setImageBitmap(BitmapFactory.decodeFile(book?.resImage.toString()))
        etName.setText(book?.name.toString())
        etAuthor.setText(book?.author.toString())
        etPublishingHouse.setText(book?.publishingHouse.toString())
        etISBN.setText(book?.isbn.toString())

        btnCancel.setOnClickListener { finish() }

        btnSave.setOnClickListener {
            if (book != null) {
                updateBook(book)
            }

        }
    }

    private fun updateBook(book: Book) {
        if (validationData()){
            setBook(book)
            BookDao(this@EditBookActivity.applicationContext).updateBook(book)
            finish()
            launchBookActivity(book)
        }
    }

    private fun launchBookActivity(book: Book) {
        startActivity(
            Intent(this, BookActivity::class.java)
                .putExtra("BOOK",book)
        )
    }

    private fun setBook(book: Book) {
        book.name = etName.text.toString()
        book.author = etAuthor.text.toString()
        book.publishingHouse = etPublishingHouse.text.toString()
        book.isbn = etISBN.text.toString()
    }

    private fun validationData(): Boolean {
        var validation = false

        if (validateField(etName) &&
            validateField(etAuthor) &&
            validateField(etPublishingHouse) &&
            validateField(etISBN)){
            validation = true
        }

        return validation
    }

    private fun validateField(editText: EditText): Boolean {
        var result = true
        if (editText.text.toString().isNullOrEmpty()){
            editText.error = "Fill field"
            result = false
        }

        return result
    }


}