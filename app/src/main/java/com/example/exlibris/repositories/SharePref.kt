package com.example.exlibris.repositories

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.exlibris.LOGED
import com.example.exlibris.USER_NAME
import com.example.exlibris.data.User

class SharePref(private var sharedPreferences: SharedPreferences,
                private var context: Context
): ISharePref {
    override fun GetPreference(preferenceName: String, success: () -> Unit, error: () -> Unit) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    override fun EditPreference(
        preferenceName: String,
        userName: String,
        success: (User) -> Unit,
        error: () -> Unit
    ) {
        sharedPreferences.edit().apply {
            putBoolean(LOGED, true)
            putString(USER_NAME, userName)
            apply()
        }
    }
}