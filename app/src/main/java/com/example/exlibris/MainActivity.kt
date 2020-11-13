package com.example.exlibris

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.toolbar.*

const val CAMBIAR_NOMBRE = "CambiarNombre"

class MainActivity : AppCompatActivity() {

    private lateinit var fabAddBook: FloatingActionButton

    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()

    }

    private fun setupUI() {
        setSupportActionBar(findViewById(R.id.toolbar))
        fabAddBook = findViewById(R.id.floatingActionButton)
        fabAddBook.setOnClickListener{ launchAddBookActivity() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemSettings) {
            startActivity(
                    Intent(this, com.example.exlibris.preferences.PreferenceActivity::class.java)
            )
        }
        if (item.itemId == R.id.itemAboutMe) {
            startActivity(
                    Intent(this, com.example.exlibris.preferences.AboutMeActivity::class.java)
            )}

        return super.onOptionsItemSelected(item)


    }

    override fun onResume() {
        super.onResume()
        cambiarTitulo()
    }

    private fun cambiarTitulo(){
        val nombre = preferences.getString(CAMBIAR_NOMBRE, "Mi Biblioteca")
        supportActionBar?.title = "La Biblioteca De $nombre"
    }

    
    private fun launchAddBookActivity(){
        startActivity(
                Intent(this, AddBookActivity::class.java)
        )
    }
}