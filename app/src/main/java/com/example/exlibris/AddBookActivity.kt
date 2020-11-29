package com.example.exlibris

import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.exlibris.adapter.COMPLETED_BOOK
import com.example.exlibris.data.Book
import com.example.exlibris.db.BookDao
import com.example.exlibris.notifications.BookNotif
import com.example.exlibris.utils.Camera
import com.example.exlibris.utils.Keyboard
import com.google.android.material.textfield.TextInputEditText



class AddBookActivity : AppCompatActivity() {

    private lateinit var etName: TextInputEditText
    private lateinit var etAuthor: TextInputEditText
    private lateinit var etPublishingHouse: TextInputEditText
    private lateinit var etISBN: TextInputEditText

    private lateinit var camera: Camera
    private lateinit var ivBookCover: ImageView
    private lateinit var btnSave: Button
    private lateinit var btnTakePhoto: Button
    private lateinit var pathBookCover: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        setupUI()
    }

    private fun setupUI() {
        etName = findViewById(R.id.etName)
        etAuthor = findViewById(R.id.etAuthor)
        etPublishingHouse = findViewById(R.id.etPublishingHouse)
        etISBN = findViewById(R.id.etISBN)

        btnSave = findViewById(R.id.btnSave)
        btnTakePhoto = findViewById(R.id.btnTakePhoto)
        ivBookCover = findViewById(R.id.imgBookCover)

        camera = Camera(this, ivBookCover)

        btnTakePhoto.setOnClickListener { camera.takePhoto() }
        btnSave.setOnClickListener { saveBook() }
    }

    private fun saveBook() {
        pathBookCover = camera.getPath()
        validateData()

        if (isDataValid()){
            var book = createBook()
            BookDao(this@AddBookActivity.applicationContext).addBook(book)
            Keyboard.hideKeyboard(this)
            book = BookDao(this@AddBookActivity.applicationContext).getBook(book.resImage)
            launchBookActivity(book)
            showNotification(book)
            finish()
        } else {
            showMessage("Debe ingresar datos validos")
        }

    }

    private fun launchBookActivity(book: Book) {
        val intent = Intent(this, BookActivity::class.java)
        intent.putExtra(COMPLETED_BOOK, book)
        startActivity(intent)
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message,Toast.LENGTH_LONG).show()
    }


    private fun validateData() {
        validateField(etName)
        validateField(etAuthor)
        validateField(etPublishingHouse)
        validateField(etISBN)
        validatePhoto()
    }

    private fun createBook(): Book {
        return Book(
                pathBookCover,
                getTextFrom(etName),
                getTextFrom(etAuthor),
                getTextFrom(etPublishingHouse),
                getTextFrom(etISBN)
        )
    }

    private fun validatePhoto() = pathBookCover.isNotEmpty()

    private fun validateField(editText: EditText) {
        if (getTextFrom(editText).isEmpty()) {
            editText.error = "Fill field"
        }
    }

    private fun getTextFrom(editText: EditText) = editText.text.toString()

    private fun isDataValid() = etName.error.isNullOrEmpty() && etAuthor.error.isNullOrEmpty() && etPublishingHouse.error.isNullOrEmpty() && etISBN.error.isNullOrEmpty() && validatePhoto()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        camera?.activityResult(requestCode, resultCode, data)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        camera?.requestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun showNotification(book: Book) {
        BookNotif.createNotificationForNewBook(this)

        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        NotificationCompat.Builder(this, BookNotif.NEW_BOOK_ID)
                .setSmallIcon(R.mipmap.ic_launcher_exlibris)
                .setContentTitle("Nuevo Libro Agregado")
                .setContentText("Ingrese y observe el catalogo actualizado")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
                .also { notification ->
                    NotificationManagerCompat
                            .from(this)
                            .notify(1, notification)
                }
    }

}