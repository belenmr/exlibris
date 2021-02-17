package com.example.exlibris

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exlibris.adapter.BookAdapter
import com.example.exlibris.db.BookDao
import com.example.exlibris.preferences.LIBRARY_OWNER
import com.example.exlibris.preferences.SWITCH_CUSTOMIZE
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*

const val DEF_TOOLBAR = "Mi Biblioteca"
const val CUSTOMIZED_TOOBAR = "Biblioteca de "
const val DEF_VALUE_OWNER = "Exlibris"

class MainActivity : AppCompatActivity() {


    private lateinit var fabAddBook: FloatingActionButton

    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        printFirebaseToken()
        setupUI()
    }

    private fun printFirebaseToken() {
        val TAG = "TOKEN LIBRIS"
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }


            val token = task.result

            Log.d(TAG, token.orEmpty())

        })
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
        val shouldCustomizeName = preferences.getBoolean(SWITCH_CUSTOMIZE, false)
        val owner = preferences.getString(LIBRARY_OWNER, DEF_VALUE_OWNER)
        if (shouldCustomizeName && !owner.isNullOrBlank()){
            supportActionBar?.title  = CUSTOMIZED_TOOBAR + owner
        } else {
            supportActionBar?.title = DEF_TOOLBAR
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