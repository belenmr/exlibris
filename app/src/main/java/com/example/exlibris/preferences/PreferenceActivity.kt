package com.example.exlibris.preferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import com.example.exlibris.R
import kotlinx.android.synthetic.main.activity_preference.*



class PreferenceActivity : AppCompatActivity() {

    private lateinit var eTnombreBiblioteca: EditText
    private lateinit var btnCambiarNombre : Button
    private lateinit var sWcambiarNombre : Switch
    private lateinit var tVnombre: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)
        setSupportActionBar(findViewById(R.id.toolbar))
        setupUI()

    }

    private fun setupUI()
    {
        eTnombreBiblioteca = findViewById(R.id.eTnombreBiblioteca)
        btnCambiarNombre = findViewById(R.id.BtnCambiarNombre)
        sWcambiarNombre = findViewById(R.id.sWcambiarNombre)
        tVnombre = findViewById(R.id.tVnombre)
        btnCambiarNombre.setOnClickListener{
            cambiarNombre()}
        sWcambiarNombre.setOnClickListener { mostrarPreferencia() }
    }


    private fun mostrarPreferencia()
    {
        if (sWcambiarNombre.isChecked)
        {
            tVnombre.visibility = View.VISIBLE
            eTnombreBiblioteca.visibility = View.VISIBLE
            btnCambiarNombre.visibility = View.VISIBLE
        }
        if (!sWcambiarNombre.isChecked)
        {
            tVnombre.visibility = View.INVISIBLE
            eTnombreBiblioteca.visibility = View.INVISIBLE
            btnCambiarNombre.visibility = View.INVISIBLE
        }
    }




    private fun cambiarNombre() {
        val nombre = eTnombreBiblioteca.text.toString()

        if (nombre.isNotEmpty()) {
            actionBar?.title = "La Biblioteca De $nombre"
            }

        else {
            mensajeAdvertencia("Completar nombre")
        }
    }

    private fun mensajeAdvertencia(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}