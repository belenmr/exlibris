package com.example.exlibris

import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.exlibris.data.Book
import com.example.exlibris.notifications.BookNotif
import com.example.exlibris.utils.Camera
import com.example.exlibris.utils.Keyboard
import com.google.android.material.textfield.TextInputEditText


class AddBookActivity : AppCompatActivity() {

    private lateinit var etName: TextInputEditText
    private lateinit var etNameAuthor: TextInputEditText
    private lateinit var etPublishingHouse: TextInputEditText
    private lateinit var etISBN: TextInputEditText
    private lateinit var checkRead: CheckBox
    private lateinit var camera: Camera
    private lateinit var ivBookCover: ImageView
    private lateinit var btnSave: Button
    private lateinit var btnTakePhoto: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        setupUI()
    }

    private fun setupUI() {
        etName = findViewById(R.id.etName)
        etNameAuthor = findViewById(R.id.etAuthor)
        etPublishingHouse = findViewById(R.id.etPublishingHouse)
        etISBN = findViewById(R.id.etISBN)
        checkRead = findViewById(R.id.checkRead)
        btnSave = findViewById(R.id.btnSave)
        btnTakePhoto = findViewById(R.id.btnTakePhoto)
        ivBookCover = findViewById(R.id.imgBookCover)

        camera = Camera(this, ivBookCover)

        btnTakePhoto.setOnClickListener { camera.takePhoto() }
        btnSave.setOnClickListener { saveBook() }
    }

    private fun saveBook() {
        var read = checkRead.isChecked

        Keyboard.hideKeyboard(this)
    }

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

        val intent = Intent(this, BookActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            putExtra("", book)
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        NotificationCompat.Builder(this, BookNotif.NEW_BOOK_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Nuevo Libro Agregado")
                .setContentText("Ingrese y observe el nuevo libro")
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