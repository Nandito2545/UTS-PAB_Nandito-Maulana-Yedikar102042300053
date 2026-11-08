package com.uts.inventaris053.utils

import java.text.NumberFormat
import java.util.Locale

/**
 * Utilitas pemformat nominal Rupiah (Rp)
 * NIM: 102042300053
 */
object CurrencyHelper_053 {

    fun formatRupiah_053(nominal_053: Long): String {
        val localeId_053 = Locale("in", "ID")
        val formatter_053 = NumberFormat.getNumberInstance(localeId_053)
        return "Rp ${formatter_053.format(nominal_053)}"
    }
}

