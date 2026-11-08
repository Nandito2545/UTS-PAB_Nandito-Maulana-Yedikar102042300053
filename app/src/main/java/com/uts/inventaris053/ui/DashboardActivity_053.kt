package com.uts.inventaris053.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.uts.inventaris053.data.database.AppDatabase_053
import com.uts.inventaris053.data.entity.Item_053
import com.uts.inventaris053.databinding.ActivityDashboard053Binding
import com.uts.inventaris053.ui.adapter.ItemAdapter_053
import com.uts.inventaris053.utils.CurrencyHelper_053
import com.uts.inventaris053.utils.SessionManager_053
import kotlinx.coroutines.launch

/**
 * Halaman Dashboard & Daftar Inventaris Barang (RecyclerView)
 * Menampilkan Total Nilai Inventaris secara Real-Time (Bonus Kelompok 4) + Fitur Pencarian Barang.
 * NIM: 102042300053
 */
class DashboardActivity_053 : AppCompatActivity() {

    private lateinit var binding_053: ActivityDashboard053Binding
    private lateinit var database_053: AppDatabase_053
    private lateinit var sessionManager_053: SessionManager_053
    private lateinit var itemAdapter_053: ItemAdapter_053

    private var currentUserId_053: Int = -1
    private var currentItemsLiveData_053: LiveData<List<Item_053>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sessionManager_053 = SessionManager_053(this)
        if (!sessionManager_053.isLoggedIn_053()) {
            redirectToLogin_053()
            return
        }

        currentUserId_053 = sessionManager_053.getUserId_053()

        binding_053 = ActivityDashboard053Binding.inflate(layoutInflater)
        setContentView(binding_053.root)

        database_053 = AppDatabase_053.getDatabase_053(this)

        setupHeader_053()
        setupRecyclerView_053()
        observeDashboardSummary_053()
        observeItems_053("")
        setupSearchBar_053()
        setupActionButtons_053()
    }

    private fun setupHeader_053() {
        val username_053 = sessionManager_053.getUsername_053()
        binding_053.tvGreetingUser053.text = "Halo, $username_053!"
    }

    private fun setupRecyclerView_053() {
        itemAdapter_053 = ItemAdapter_053(
            onEditClick_053 = { item_053 ->
                val intentEdit_053 = Intent(this, FormItemActivity_053::class.java).apply {
                    putExtra(FormItemActivity_053.EXTRA_ITEM_ID_053, item_053.id_053)
                }
                startActivity(intentEdit_053)
            },
            onDeleteClick_053 = { item_053 ->
                showDeleteConfirmationDialog_053(item_053)
            }
        )

        binding_053.rvItems053.apply {
            layoutManager = LinearLayoutManager(this@DashboardActivity_053)
            adapter = itemAdapter_053
        }
    }

    /**
     * Observasi Real-Time Total Nilai Inventaris & Ringkasan Stok di Dashboard (Bonus Kelompok 4)
     */
    private fun observeDashboardSummary_053() {
        // Total nilai inventaris dari query SUM(jumlah_053 * harga_053)
        database_053.itemDao_053()
            .getTotalInventoryValue_053(currentUserId_053)
            .observe(this) { totalNilai_053 ->
                val safeTotal_053 = totalNilai_053 ?: 0L
                binding_053.tvTotalNilai053.text = CurrencyHelper_053.formatRupiah_053(safeTotal_053)
            }

        // Total jenis barang dan total unit stok milik user
        database_053.itemDao_053()
            .getItemsByUser_053(currentUserId_053)
            .observe(this) { allUserItems_053 ->
                val totalJenis_053 = allUserItems_053.size
                val totalStok_053 = allUserItems_053.sumOf { it.jumlah_053 }
                binding_053.tvTotalJenis053.text = "$totalJenis_053 Item"
                binding_053.tvTotalStok053.text = "$totalStok_053 Unit"
            }
    }

    /**
     * Observasi daftar barang (dengan atau tanpa kata kunci pencarian)
     */
    private fun observeItems_053(keyword_053: String) {
        currentItemsLiveData_053?.removeObservers(this)

        val liveData_053 = if (keyword_053.isBlank()) {
            database_053.itemDao_053().getItemsByUser_053(currentUserId_053)
        } else {
            database_053.itemDao_053().searchItemsByUser_053(currentUserId_053, keyword_053)
        }

        currentItemsLiveData_053 = liveData_053
        liveData_053.observe(this) { items_053 ->
            itemAdapter_053.submitList_053(items_053)

            if (items_053.isEmpty()) {
                binding_053.layoutEmpty053.visibility = View.VISIBLE
                binding_053.rvItems053.visibility = View.GONE
                binding_053.tvEmptyMessage053.text = if (keyword_053.isBlank()) {
                    "Belum ada data barang.\nTekan tombol '+ Tambah Barang' untuk menambahkan."
                } else {
                    "Barang dengan kata kunci \"$keyword_053\" tidak ditemukan."
                }
            } else {
                binding_053.layoutEmpty053.visibility = View.GONE
                binding_053.rvItems053.visibility = View.VISIBLE
            }
        }
    }

    private fun setupSearchBar_053() {
        binding_053.etSearch053.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s_053: CharSequence?, start_053: Int, count_053: Int, after_053: Int) {}

            override fun onTextChanged(s_053: CharSequence?, start_053: Int, before_053: Int, count_053: Int) {
                val query_053 = s_053?.toString()?.trim().orEmpty()
                observeItems_053(query_053)
            }

            override fun afterTextChanged(s_053: Editable?) {}
        })
    }

    private fun setupActionButtons_053() {
        binding_053.fabAddItem053.setOnClickListener {
            val intentAdd_053 = Intent(this, FormItemActivity_053::class.java)
            startActivity(intentAdd_053)
        }

        binding_053.btnLogout053.setOnClickListener {
            showLogoutConfirmationDialog_053()
        }
    }

    private fun showDeleteConfirmationDialog_053(item_053: Item_053) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Barang")
            .setMessage("Apakah Anda yakin ingin menghapus \"${item_053.nama_053}\" dari inventaris?")
            .setPositiveButton("Hapus") { _, _ ->
                lifecycleScope.launch {
                    database_053.itemDao_053().deleteItem_053(item_053)
                    Toast.makeText(
                        this@DashboardActivity_053,
                        "Barang \"${item_053.nama_053}\" berhasil dihapus",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun showLogoutConfirmationDialog_053() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Logout")
            .setMessage("Apakah Anda yakin ingin keluar dari sesi akun ini?")
            .setPositiveButton("Ya, Logout") { _, _ ->
                sessionManager_053.clearSession_053()
                redirectToLogin_053()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun redirectToLogin_053() {
        val intentLogin_053 = Intent(this, LoginActivity_053::class.java)
        intentLogin_053.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intentLogin_053)
        finish()
    }
}

