package com.example.exlibris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
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



}