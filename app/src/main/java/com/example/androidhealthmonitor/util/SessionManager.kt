package com.example.androidhealthmonitor.util

import android.content.Context

class SessionManager(private val ctx: Context) {
    private val prefs = ctx.getSharedPreferences("health_prefs", Context.MODE_PRIVATE)

    fun saveUser(username: String, password: String) {
        prefs.edit().putString("user_name", username).putString("user_pass", password).apply()
    }

    fun updateUsername(newUsername: String) {
        val password = prefs.getString("user_pass", null) ?: return
        // Update both the registration username and the logged-in session username
        prefs.edit()
            .putString("user_name", newUsername)
            .putString("logged_in_user", newUsername)
            .apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.contains("logged_in_user")
    }

    fun login(username: String) {
        prefs.edit().putString("logged_in_user", username).apply()
    }

    fun logout() {
        prefs.edit().remove("logged_in_user").apply()
    }

    fun getLoggedInUser(): String? = prefs.getString("logged_in_user", null)

    fun registerExists(username: String, password: String): Boolean {
        val stored = prefs.getString("user_name", null)
        val pass = prefs.getString("user_pass", null)
        return stored == username && pass == password
    }
}
