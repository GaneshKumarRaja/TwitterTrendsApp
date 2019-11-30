package com.ganesh.twitterapp.data.persistence


import android.content.SharedPreferences
import javax.inject.Inject

const val TOKEN = "token"

class KeyValueHandler @Inject constructor(
    private var pref: SharedPreferences,
    private var editor: SharedPreferences.Editor
) {

    fun getToken(): String? {
        return pref.getString(TOKEN, "")
    }


    fun setToken(token: String) {
        editor.putString(TOKEN, token)
        editor.commit()
    }

}