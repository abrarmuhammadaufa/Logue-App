package com.example.logue11.main.sharedpreferences

import android.content.Context

internal class UserPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val USERID = "userid"
        private const val FULLNAME = "fullname"
        private const val USERNAME = "username"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
        private const val TOKEN = "token"
        private const val STATUS = "status"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    fun setUser(account: List<List<Any>>, token: String){
        val editor = preferences.edit()
        val userid = account[0][0]
        val fullname = account[0][1]
        val username = account[0][2]
        val email = account[0][3]
        val password = account[0][4]

        editor.putBoolean(STATUS, true)
        editor.putString(USERID, userid.toString())
        editor.putString(FULLNAME, fullname.toString())
        editor.putString(USERNAME, username.toString())
        editor.putString(EMAIL, email.toString())
        editor.putString(PASSWORD, password.toString())
        editor.putString(TOKEN, token)
        editor.apply()
    }

    fun loginStatus(): Boolean{
        return preferences.getBoolean(STATUS,false)
    }

    fun removeUser() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

    fun getFullName(): String? {
        return preferences.getString(FULLNAME, "")
    }

    fun getUsername(): String? {
        return preferences.getString(USERNAME, "")
    }

    fun getEmail(): String? {
        return preferences.getString(EMAIL, "")
    }

}