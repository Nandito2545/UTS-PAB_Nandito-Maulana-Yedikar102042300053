package com.uts.inventaris053

import com.uts.inventaris053.utils.CurrencyHelper_053
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit Test untuk menguji fungsi pemformatan Rupiah pada CurrencyHelper_053
 * NIM: 102042300053 (053)
 */
class CurrencyHelperTest_053 {

    @Test
    fun testFormatRupiah_Zero_053() {
        val result_053 = CurrencyHelper_053.formatRupiah_053(0L)
        assertEquals("Rp 0", result_053)
    }

    @Test
    fun testFormatRupiah_Thousands_053() {
        val result_053 = CurrencyHelper_053.formatRupiah_053(15000L)
        assertEquals("Rp 15.000", result_053)
    }

    @Test
    fun testFormatRupiah_Millions_053() {
        val result_053 = CurrencyHelper_053.formatRupiah_053(25000000L)
        assertEquals("Rp 25.000.000", result_053)
    }
}
