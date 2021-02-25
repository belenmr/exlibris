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
import com.example.exlibris.data.Book
import com.example.exlibris.db.BookDao
import com.example.exlibris.preferences.LIBRARY_OWNER
import com.example.exlibris.preferences.SWITCH_CUSTOMIZE
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

const val DEF_TOOLBAR = "Mi Biblioteca"
const val CUSTOMIZED_TOOBAR = "Biblioteca de "
const val DEF_VALUE_OWNER = "Exlibris"

class MainActivity : AppCompatActivity() {


    private lateinit var fabAddBook: FloatingActionButton
    private val adapter: BookAdapter by lazy { BookAdapter(this) }
    private val compositeDisposable = CompositeDisposable()
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
        var owner = DEF_VALUE_OWNER

        Single.fromCallable { preferences.getString(LIBRARY_OWNER, DEF_VALUE_OWNER) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<String?> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(prefOwner: String) {
                    owner = prefOwner
                }

                override fun onError(e: Throwable) {
                    Log.i("MainActivity", "Error al obtener preferencias - LibraryOwner", e)
                }

            })

        Single.fromCallable { preferences.getBoolean(SWITCH_CUSTOMIZE, false) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: SingleObserver<Boolean>{
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(shouldCustomizeName: Boolean) {
                    if (shouldCustomizeName && !owner.isNullOrBlank()){
                        supportActionBar?.title  = CUSTOMIZED_TOOBAR + owner
                    } else {
                        supportActionBar?.title = DEF_TOOLBAR
                    }
                }

                override fun onError(e: Throwable) {
                    Log.i("MainActivity", "Error al obtener preferencias - shouldCustomizeName", e)
                }

            })
    }

    private fun launchAddBookActivity(){
        startActivity(
                Intent(this, AddBookActivity::class.java)
        )
    }

    fun initRecycler(){
        rvBooks.layoutManager = LinearLayoutManager(this)
        BookDao(this)
            .getBooks()
            .subscribe(object : SingleObserver<List<Book>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(books: List<Book>) {
                    adapter.updateBooks(books)
                    rvBooks.adapter = adapter
                }

                override fun onError(e: Throwable) {
                    Log.i("MainActivity", "Error al obtener la lista de libros", e)
                }
            })
    }

}