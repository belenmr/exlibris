package com.example.exlibris

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exlibris.adapter.BookAdapter
import com.example.exlibris.data.Book
import com.example.exlibris.db.BookDao
import com.example.exlibris.news.NewsActivity
import com.example.exlibris.preferences.LIBRARY_OWNER
import com.example.exlibris.preferences.SWITCH_CUSTOMIZE
import com.example.exlibris.ui.LoginActivity
import com.example.exlibris.ui.RegisterActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
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
const val LOGED = "loged"
const val USER_NAME = "userName"

class MainActivity : AppCompatActivity() {


    private lateinit var fabAddBook: FloatingActionButton
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar
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
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
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
        setupDrawer()
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

    private fun setupDrawer() {

        navView = findViewById(R.id.navigationView)

        drawerLayout = findViewById(R.id.drawerLayout)
        if (preferences.getBoolean(LOGED, false)) {

            setVisibilityLogued(preferences.getString(USER_NAME, ""))
        } else {
            setVisibilityNotLogued()
        }

        val drawertoggle =
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(drawertoggle)

        drawertoggle.syncState()
        selectNavigation()
    }

    private fun selectNavigation() {
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.it_registro -> {

                    this.drawerLayout.closeDrawer(GravityCompat.START)
                    launchRegister()
                    true
                }
                R.id.it_login -> {
                    this.drawerLayout.closeDrawer(GravityCompat.START)
                    launchLogin()
                    true
                }
                R.id.it_agregarLibro -> {
                    this.drawerLayout.closeDrawer(GravityCompat.START)
                    launchAddBookActivity()
                    true
                }
                R.id.it_novedades -> {
                    this.drawerLayout.closeDrawer(GravityCompat.START)
                    launchNovedades()
                    true
                }
                R.id.it_deslog -> {
                    this.drawerLayout.closeDrawer(GravityCompat.START)
                    setLogout()
                    setupDrawer()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun launchLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun launchRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun launchNovedades() {
        startActivity(Intent(this, NewsActivity::class.java))
    }

    private fun setLogout() {
        preferences.edit().apply {
            putBoolean(LOGED, false)
            putString(USER_NAME, "")
            apply()
        }
    }

    private fun setVisibilityLogued(username: String?) {

        navView.getHeaderView(0).findViewById<TextView>(R.id.nav_header_textView).text = username
        navView.getHeaderView(0).findViewById<TextView>(R.id.nav_header_textView).visibility =
                View.VISIBLE
        navView.menu.findItem(R.id.it_login).setVisible(false)
        navView.menu.findItem(R.id.it_registro).setVisible(false)

    }

    private fun setVisibilityNotLogued() {
        navView.getHeaderView(0).findViewById<TextView>(R.id.nav_header_textView).visibility =
                View.GONE
        navView.menu.findItem(R.id.it_login).isVisible = true
        navView.menu.findItem(R.id.it_registro).isVisible = true
        navView.menu.clear()
        navView.inflateMenu(R.menu.nav_items)
    }

    override fun onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        drawerLayout.openDrawer(navView)
        return true
    }


}
