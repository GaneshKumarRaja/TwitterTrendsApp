package com.ganesh.twitterapp.util


import android.content.SharedPreferences
import javax.inject.Inject

class SheredPref @Inject constructor(
    private var pref: SharedPreferences,
    private var editor: SharedPreferences.Editor
) {


    fun setString(key: String, toekn: String) {
        editor.putString(key, toekn)
        editor.commit()
    }

    fun getString(key: String): String? {
        return pref.getString(key, "")
    }

}