package com.ganesh.twitterapp.util

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SheredPref @Inject constructor(context: Context, name: String) {
    val editor: SharedPreferences.Editor
    val pref: SharedPreferences

    init {
        pref = context.getApplicationContext().getSharedPreferences(name, 0)
        editor = pref.edit()
    }


    fun setString(key: String, toekn: String) {
        editor.putString(key, toekn)
        editor.commit()
    }

    fun getString(key: String): String {
        return pref.getString(key, "")!!
    }

}