package com.example.exlibris.preferences

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.example.exlibris.R

class AboutMeActivity : AppCompatActivity() {

    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_me)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onResume() {
        super.onResume()
        cambiarTitulo()
    }

    private fun cambiarTitulo(){
        val nombre = preferences.getString(com.example.exlibris.CAMBIAR_NOMBRE, "Mi Biblioteca")
        supportActionBar?.title = "La Biblioteca De $nombre"
    }
}