package com.uts.inventaris053.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Manajemen Sesi Login menggunakan SharedPreferences
 * Menyimpan user_id_053 agar data inventaris tiap user terisolasi.
 * NIM: 102042300053
 */
class SessionManager_053(context_053: Context) {

    private val prefs_053: SharedPreferences = context_053.getSharedPreferences(
        PREF_NAME_053,
        Context.MODE_PRIVATE
    )

    companion object {
        private const val PREF_NAME_053 = "inventaris_session_053"
        private const val KEY_IS_LOGGED_IN_053 = "is_logged_in_053"
        private const val KEY_USER_ID_053 = "user_id_053"
        private const val KEY_USERNAME_053 = "username_053"
    }

    fun saveSession_053(userId_053: Int, username_053: String) {
        prefs_053.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN_053, true)
            putInt(KEY_USER_ID_053, userId_053)
            putString(KEY_USERNAME_053, username_053)
            apply()
        }
    }

    fun isLoggedIn_053(): Boolean {
        return prefs_053.getBoolean(KEY_IS_LOGGED_IN_053, false) && getUserId_053() != -1
    }

    fun getUserId_053(): Int {
        return prefs_053.getInt(KEY_USER_ID_053, -1)
    }

    fun getUsername_053(): String {
        return prefs_053.getString(KEY_USERNAME_053, "User") ?: "User"
    }

    fun clearSession_053() {
        prefs_053.edit().clear().apply()
    }
}

