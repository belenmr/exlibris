package com.example.exlibris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
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

        return super.onOptionsItemSelected(item)


    }
/*
    private fun launchSettings() {
        startActivity(
                Intent(this, PreferenceActivity::class.java)
        )
    }*/
}