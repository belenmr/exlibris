package com.example.exlibris.preferences

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceManager
import com.example.exlibris.R
import kotlinx.android.synthetic.main.activity_preference.*

const val CAMBIAR_NOMBRE = "CambiarNombre"

class PreferenceActivity : AppCompatActivity() {


    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)
        setSupportActionBar(findViewById(R.id.toolbar))
        showPreferencesFragment()

    }

    private fun showPreferencesFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contenedorAjustes, PreferenceFragment())
                .commit()
    }


    private fun cambiarNombre() {

        val nombre = preferences.getString(CAMBIAR_NOMBRE,"Mi Biblioteca")

        if (nombre != "Mi Biblioteca")
        {
            supportActionBar?.title  = "La Biblioteca De $nombre"
        }
        else
        {
            supportActionBar?.title = "Mi Biblioteca"
        }

    }


    override fun onResume() {
        super.onResume()
        cambiarNombre()
    }


}