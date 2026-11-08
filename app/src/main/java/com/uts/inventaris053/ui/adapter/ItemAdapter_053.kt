package com.uts.inventaris053.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uts.inventaris053.data.entity.Item_053
import com.uts.inventaris053.databinding.ItemInventory053Binding
import com.uts.inventaris053.utils.CurrencyHelper_053

/**
 * Adapter RecyclerView untuk menampilkan daftar Inventaris Barang milik user yang login
 * Menggunakan ViewBinding (ItemInventory053Binding)
 * NIM: 102042300053
 */
class ItemAdapter_053(
    private val onEditClick_053: (Item_053) -> Unit,
    private val onDeleteClick_053: (Item_053) -> Unit
) : RecyclerView.Adapter<ItemAdapter_053.ItemViewHolder_053>() {

    private val listBarang_053 = mutableListOf<Item_053>()

    fun submitList_053(newList_053: List<Item_053>) {
        listBarang_053.clear()
        listBarang_053.addAll(newList_053)
        notifyDataSetChanged()
    }

    inner class ItemViewHolder_053(
        private val binding_053: ItemInventory053Binding
    ) : RecyclerView.ViewHolder(binding_053.root) {

        fun bind_053(item_053: Item_053) {
            val hargaFormatted_053 = CurrencyHelper_053.formatRupiah_053(item_053.harga_053)
            val subtotalNilai_053 = item_053.jumlah_053.toLong() * item_053.harga_053
            val subtotalFormatted_053 = CurrencyHelper_053.formatRupiah_053(subtotalNilai_053)

            binding_053.tvItemNama053.text = item_053.nama_053
            binding_053.tvItemHargaJumlah053.text =
                "${item_053.jumlah_053} Unit × $hargaFormatted_053"
            binding_053.tvItemSubtotal053.text = subtotalFormatted_053

            binding_053.btnItemEdit053.setOnClickListener {
                onEditClick_053(item_053)
            }

            binding_053.btnItemDelete053.setOnClickListener {
                onDeleteClick_053(item_053)
            }
        }
    }

    override fun onCreateViewHolder(parent_053: ViewGroup, viewType_053: Int): ItemViewHolder_053 {
        val binding_053 = ItemInventory053Binding.inflate(
            LayoutInflater.from(parent_053.context),
            parent_053,
            false
        )
        return ItemViewHolder_053(binding_053)
    }

    override fun onBindViewHolder(holder_053: ItemViewHolder_053, position_053: Int) {
        holder_053.bind_053(listBarang_053[position_053])
    }

    override fun getItemCount(): Int = listBarang_053.size
}

