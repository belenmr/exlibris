package com.example.exlibris.preferences

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.example.exlibris.R

const val LIBRARY_OWNER = "OwnerName"
const val SWITCH_CUSTOMIZE = "switchCustomizeNameLibrary"
const val PREFERENCE_TOOLBAR = "Configuraciones"

class PreferenceActivity : AppCompatActivity() {


    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        setupUI()
    }

    private fun setupUI() {
        setupToolbar()
        showPreferencesFragment()
    }

    private fun setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = PREFERENCE_TOOLBAR
    }

    private fun showPreferencesFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.settingsContainer, PreferenceFragment())
                .commit()
    }

}