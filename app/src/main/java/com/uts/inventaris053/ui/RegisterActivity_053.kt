package com.uts.inventaris053.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.uts.inventaris053.data.database.AppDatabase_053
import com.uts.inventaris053.data.entity.User_053
import com.uts.inventaris053.databinding.ActivityRegister053Binding
import kotlinx.coroutines.launch

/**
 * Halaman Registrasi User Baru
 * Mencakup validasi input kosong, password minimal 6 karakter, dan username unik.
 * NIM: 102042300053 (Kelompok 4)
 */
class RegisterActivity_053 : AppCompatActivity() {

    private lateinit var binding_053: ActivityRegister053Binding
    private lateinit var database_053: AppDatabase_053

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_053 = ActivityRegister053Binding.inflate(layoutInflater)
        setContentView(binding_053.root)

        database_053 = AppDatabase_053.getDatabase_053(this)

        setupListeners_053()
    }

    private fun setupListeners_053() {
        binding_053.btnRegister053.setOnClickListener {
            performRegister_053()
        }

        binding_053.tvGoLogin053.setOnClickListener {
            finish()
        }
    }

    private fun performRegister_053() {
        val usernameInput_053 = binding_053.etUsernameRegister053.text.toString().trim()
        val passwordInput_053 = binding_053.etPasswordRegister053.text.toString().trim()
        val confirmPasswordInput_053 = binding_053.etConfirmPasswordRegister053.text.toString().trim()

        binding_053.tilUsernameRegister053.error = null
        binding_053.tilPasswordRegister053.error = null
        binding_053.tilConfirmPasswordRegister053.error = null

        // 1. Validasi input kosong
        if (usernameInput_053.isEmpty()) {
            binding_053.tilUsernameRegister053.error = "Username wajib diisi"
            binding_053.etUsernameRegister053.requestFocus()
            return
        }

        if (passwordInput_053.isEmpty()) {
            binding_053.tilPasswordRegister053.error = "Password wajib diisi"
            binding_053.etPasswordRegister053.requestFocus()
            return
        }

        // 2. Validasi password minimal 6 karakter (Sesuai Rubrik Data Integrity & Validation)
        if (passwordInput_053.length < 6) {
            binding_053.tilPasswordRegister053.error = "Password minimal harus 6 karakter"
            binding_053.etPasswordRegister053.requestFocus()
            return
        }

        if (confirmPasswordInput_053 != passwordInput_053) {
            binding_053.tilConfirmPasswordRegister053.error = "Konfirmasi password tidak cocok"
            binding_053.etConfirmPasswordRegister053.requestFocus()
            return
        }

        // 3. Validasi username unik & simpan ke Room Database
        lifecycleScope.launch {
            val existingUser_053 = database_053.userDao_053().getUserByUsername_053(usernameInput_053)
            if (existingUser_053 != null) {
                binding_053.tilUsernameRegister053.error =
                    "Username '$usernameInput_053' sudah digunakan, pilih yang lain"
                binding_053.etUsernameRegister053.requestFocus()
                return@launch
            }

            val newUser_053 = User_053(
                username_053 = usernameInput_053,
                password_053 = passwordInput_053
            )

            database_053.userDao_053().registerUser_053(newUser_053)

            Toast.makeText(
                this@RegisterActivity_053,
                "Registrasi berhasil! Silakan login.",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }
}

