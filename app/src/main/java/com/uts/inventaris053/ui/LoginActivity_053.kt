package com.uts.inventaris053.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.uts.inventaris053.data.database.AppDatabase_053
import com.uts.inventaris053.databinding.ActivityLogin053Binding
import com.uts.inventaris053.utils.SessionManager_053
import kotlinx.coroutines.launch

/**
 * Halaman Login Aplikasi Inventaris Barang
 * NIM: 102042300053 (Kelompok 4)
 */
class LoginActivity_053 : AppCompatActivity() {

    private lateinit var binding_053: ActivityLogin053Binding
    private lateinit var database_053: AppDatabase_053
    private lateinit var sessionManager_053: SessionManager_053

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sessionManager_053 = SessionManager_053(this)

        // Jika sesi login masih aktif di SharedPreferences, langsung ke Dashboard
        if (sessionManager_053.isLoggedIn_053()) {
            navigateToDashboard_053()
            return
        }

        binding_053 = ActivityLogin053Binding.inflate(layoutInflater)
        setContentView(binding_053.root)

        database_053 = AppDatabase_053.getDatabase_053(this)

        setupListeners_053()
    }

    private fun setupListeners_053() {
        binding_053.btnLogin053.setOnClickListener {
            performLogin_053()
        }

        binding_053.tvGoRegister053.setOnClickListener {
            val intentRegister_053 = Intent(this, RegisterActivity_053::class.java)
            startActivity(intentRegister_053)
        }
    }

    private fun performLogin_053() {
        val usernameInput_053 = binding_053.etUsernameLogin053.text.toString().trim()
        val passwordInput_053 = binding_053.etPasswordLogin053.text.toString().trim()

        binding_053.tilUsernameLogin053.error = null
        binding_053.tilPasswordLogin053.error = null

        // Validasi input kosong
        if (usernameInput_053.isEmpty()) {
            binding_053.tilUsernameLogin053.error = "Username tidak boleh kosong"
            binding_053.etUsernameLogin053.requestFocus()
            return
        }

        if (passwordInput_053.isEmpty()) {
            binding_053.tilPasswordLogin053.error = "Password tidak boleh kosong"
            binding_053.etPasswordLogin053.requestFocus()
            return
        }

        // Query ke Room Database
        lifecycleScope.launch {
            val user_053 = database_053.userDao_053().loginUser_053(
                username_053 = usernameInput_053,
                password_053 = passwordInput_053
            )

            if (user_053 != null) {
                // Simpan user_id_053 ke SharedPreferences
                sessionManager_053.saveSession_053(
                    userId_053 = user_053.id_053,
                    username_053 = user_053.username_053
                )
                Toast.makeText(
                    this@LoginActivity_053,
                    "Selamat datang, ${user_053.username_053}!",
                    Toast.LENGTH_SHORT
                ).show()
                navigateToDashboard_053()
            } else {
                Toast.makeText(
                    this@LoginActivity_053,
                    "Username atau password salah!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun navigateToDashboard_053() {
        val intentDashboard_053 = Intent(this, DashboardActivity_053::class.java)
        intentDashboard_053.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intentDashboard_053)
        finish()
    }
}

