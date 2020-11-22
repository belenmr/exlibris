package com.example.exlibris

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exlibris.adapter.BookAdapter
import com.example.exlibris.db.BookDao
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*


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
            launchSettings()
        }

        if (item.itemId == R.id.itemAboutMe) {
            launchAboutMe()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun launchAboutMe() {
        startActivity(
                Intent(this, com.example.exlibris.preferences.AboutMeActivity::class.java)
        )
    }

    private fun launchSettings() {
        startActivity(
                Intent(this, com.example.exlibris.preferences.PreferenceActivity::class.java)
        )
    }


    override fun onResume() {
        super.onResume()
        handleCustomizeNameLibrary()
        initRecycler()
    }

    private fun handleCustomizeNameLibrary(){
        val shouldCustomizeName = preferences.getBoolean("switchCustomizeNameLib", false)
        val owner = preferences.getString(com.example.exlibris.preferences.LIBRARY_OWNER,"Mi Exlibris")
        if (shouldCustomizeName && !owner.isNullOrBlank()){
            supportActionBar?.title  = "Biblioteca De $owner"
        } else {
            supportActionBar?.title = "Mi Biblioteca"
        }
    }

    private fun launchAddBookActivity(){
        startActivity(
                Intent(this, AddBookActivity::class.java)
        )
    }

    fun initRecycler(){
        var books = BookDao(this).getBooks()
        rvBooks.layoutManager = LinearLayoutManager(this)
        val adapter = BookAdapter(books)
        rvBooks.adapter = adapter
    }

}