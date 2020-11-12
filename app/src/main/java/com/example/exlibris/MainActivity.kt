package com.example.exlibris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var fabAddBook: FloatingActionButton

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
/*
    private fun launchSettings() {
        startActivity(
                Intent(this, PreferenceActivity::class.java)
        )
    }*/

    private fun launchAddBookActivity(){
        startActivity(
                Intent(this, AddBookActivity::class.java)
        )
    }
}