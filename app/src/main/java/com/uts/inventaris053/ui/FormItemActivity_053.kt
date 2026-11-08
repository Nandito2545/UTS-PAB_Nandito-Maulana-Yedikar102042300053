package com.uts.inventaris053.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.uts.inventaris053.data.database.AppDatabase_053
import com.uts.inventaris053.data.entity.Item_053
import com.uts.inventaris053.databinding.ActivityFormItem053Binding
import com.uts.inventaris053.utils.CurrencyHelper_053
import com.uts.inventaris053.utils.SessionManager_053
import kotlinx.coroutines.launch

/**
 * Halaman Form Tambah & Edit Barang
 * Menyimpan barang dengan user_id_053 milik user yang sedang login.
 * NIM: 102042300053 (Kelompok 4)
 */
class FormItemActivity_053 : AppCompatActivity() {

    private lateinit var binding_053: ActivityFormItem053Binding
    private lateinit var database_053: AppDatabase_053
    private lateinit var sessionManager_053: SessionManager_053

    private var currentUserId_053: Int = -1
    private var editItemId_053: Int = -1
    private var isEditMode_053: Boolean = false

    companion object {
        const val EXTRA_ITEM_ID_053 = "extra_item_id_053"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_053 = ActivityFormItem053Binding.inflate(layoutInflater)
        setContentView(binding_053.root)

        sessionManager_053 = SessionManager_053(this)
        currentUserId_053 = sessionManager_053.getUserId_053()
        if (currentUserId_053 == -1) {
            finish()
            return
        }

        database_053 = AppDatabase_053.getDatabase_053(this)

        editItemId_053 = intent.getIntExtra(EXTRA_ITEM_ID_053, -1)
        isEditMode_053 = editItemId_053 != -1

        setupSubtotalPreviewWatcher_053()

        if (isEditMode_053) {
            binding_053.tvFormTitle053.text = "Edit Data Barang"
            binding_053.btnSimpanBarang053.text = "Perbarui Barang"
            loadExistingItemData_053(editItemId_053)
        } else {
            binding_053.tvFormTitle053.text = "Tambah Barang Baru"
            binding_053.btnSimpanBarang053.text = "Simpan Barang"
        }

        binding_053.btnSimpanBarang053.setOnClickListener {
            saveItem_053()
        }

        binding_053.btnBatal053.setOnClickListener {
            finish()
        }
    }

    private fun loadExistingItemData_053(itemId_053: Int) {
        lifecycleScope.launch {
            val item_053 = database_053.itemDao_053().getItemById_053(itemId_053, currentUserId_053)
            if (item_053 != null) {
                binding_053.etNamaBarang053.setText(item_053.nama_053)
                binding_053.etJumlahBarang053.setText(item_053.jumlah_053.toString())
                binding_053.etHargaBarang053.setText(item_053.harga_053.toString())
                updateSubtotalPreview_053()
            } else {
                Toast.makeText(
                    this@FormItemActivity_053,
                    "Data barang tidak ditemukan!",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun setupSubtotalPreviewWatcher_053() {
        val watcher_053 = object : TextWatcher {
            override fun beforeTextChanged(s_053: CharSequence?, start_053: Int, count_053: Int, after_053: Int) {}
            override fun onTextChanged(s_053: CharSequence?, start_053: Int, before_053: Int, count_053: Int) {
                updateSubtotalPreview_053()
            }
            override fun afterTextChanged(s_053: Editable?) {}
        }

        binding_053.etJumlahBarang053.addTextChangedListener(watcher_053)
        binding_053.etHargaBarang053.addTextChangedListener(watcher_053)
    }

    private fun updateSubtotalPreview_053() {
        val jumlah_053 = binding_053.etJumlahBarang053.text.toString().trim().toLongOrNull() ?: 0L
        val harga_053 = binding_053.etHargaBarang053.text.toString().trim().toLongOrNull() ?: 0L
        val subtotal_053 = jumlah_053 * harga_053
        binding_053.tvPreviewSubtotal053.text = CurrencyHelper_053.formatRupiah_053(subtotal_053)
    }

    private fun saveItem_053() {
        val namaInput_053 = binding_053.etNamaBarang053.text.toString().trim()
        val jumlahStr_053 = binding_053.etJumlahBarang053.text.toString().trim()
        val hargaStr_053 = binding_053.etHargaBarang053.text.toString().trim()

        binding_053.tilNamaBarang053.error = null
        binding_053.tilJumlahBarang053.error = null
        binding_053.tilHargaBarang053.error = null

        // Validasi input kosong & angka valid
        if (namaInput_053.isEmpty()) {
            binding_053.tilNamaBarang053.error = "Nama barang tidak boleh kosong"
            binding_053.etNamaBarang053.requestFocus()
            return
        }

        if (jumlahStr_053.isEmpty()) {
            binding_053.tilJumlahBarang053.error = "Jumlah barang wajib diisi"
            binding_053.etJumlahBarang053.requestFocus()
            return
        }

        val jumlahValue_053 = jumlahStr_053.toIntOrNull()
        if (jumlahValue_053 == null || jumlahValue_053 <= 0) {
            binding_053.tilJumlahBarang053.error = "Jumlah harus berupa angka lebih dari 0"
            binding_053.etJumlahBarang053.requestFocus()
            return
        }

        if (hargaStr_053.isEmpty()) {
            binding_053.tilHargaBarang053.error = "Harga barang wajib diisi"
            binding_053.etHargaBarang053.requestFocus()
            return
        }

        val hargaValue_053 = hargaStr_053.toLongOrNull()
        if (hargaValue_053 == null || hargaValue_053 < 0L) {
            binding_053.tilHargaBarang053.error = "Harga tidak valid"
            binding_053.etHargaBarang053.requestFocus()
            return
        }

        lifecycleScope.launch {
            if (isEditMode_053) {
                val updatedItem_053 = Item_053(
                    id_053 = editItemId_053,
                    user_id_053 = currentUserId_053,
                    nama_053 = namaInput_053,
                    jumlah_053 = jumlahValue_053,
                    harga_053 = hargaValue_053
                )
                database_053.itemDao_053().updateItem_053(updatedItem_053)
                Toast.makeText(
                    this@FormItemActivity_053,
                    "Data barang berhasil diperbarui!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val newItem_053 = Item_053(
                    user_id_053 = currentUserId_053,
                    nama_053 = namaInput_053,
                    jumlah_053 = jumlahValue_053,
                    harga_053 = hargaValue_053
                )
                database_053.itemDao_053().insertItem_053(newItem_053)
                Toast.makeText(
                    this@FormItemActivity_053,
                    "Barang baru berhasil ditambahkan!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            finish()
        }
    }
}

