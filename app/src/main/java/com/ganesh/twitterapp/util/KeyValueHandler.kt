package com.ganesh.twitterapp.util


import android.content.SharedPreferences
import javax.inject.Inject

const val TOKEN = "token"

class KeyValueHandler @Inject constructor(
    private var pref: SharedPreferences,
    private var editor: SharedPreferences.Editor
) {


//    fun setString(key: String, toekn: String) {
//        editor.putString(key, toekn)
//        editor.commit()
//    }



    fun getToken() :String?{
        return pref.getString(TOKEN, "")
    }


    fun setToken(token:String){
        editor.putString(TOKEN, token)
        editor.commit()
    }


}