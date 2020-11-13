package com.example.exlibris.preferences

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.exlibris.R

class PreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
    }

}