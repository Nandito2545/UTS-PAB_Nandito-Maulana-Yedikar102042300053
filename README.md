# UTS Pengembangan Aplikasi Bergerak (PAB) — Inventaris Barang (Kelompok 4)

## Identitas Mahasiswa
| Keterangan | Data |
| :--- | :--- |
| **Nama Lengkap** | **Nandito Maulana Yedikar** |
| **NIM** | **102042300053** |
| **3 Digit Terakhir NIM (Kode Unik)** | **`053`** |
| **Kelompok / Studi Kasus** | **Kelompok 4 — Inventaris Barang** |
| **Tools & Bahasa** | Android Studio (Kotlin, XML Views, ViewBinding) |
| **Database Lokal** | Room Persistence Library (SQLite Offline) |

---

## Deskripsi Singkat
**Inventaris Barang 053** adalah aplikasi Android berbasis **Kotlin** dan **Room Persistence Library (SQLite)** yang dirancang untuk mengelola data inventaris barang secara privat per pengguna (*multi-user data isolation*). Seluruh nama tabel, kolom entitas, kelas, fungsi, dan variabel hasil modifikasi telah menyertakan 3 digit terakhir NIM (**`_053`**) sesuai ketentuan pengerjaan UTS.

---

## Pemenuhan Rubrik Penilaian (100% + Bonus 10%)

### 1. Autentikasi & Session (`30%`)
- **Registrasi (`RegisterActivity_053`)**: Menyimpan akun pengguna baru ke tabel `user_053` di Room Database.
- **Login (`LoginActivity_053`)**: Memverifikasi kredensial (`username_053` dan `password_053`) menggunakan query Room (`UserDao_053.loginUser_053`).
- **Manajemen Sesi (`SessionManager_053`)**: Menyimpan `user_id_053` dan `username_053` di `SharedPreferences`. Mendukung *auto-login* ketika sesi masih aktif dan fitur **Logout** dengan konfirmasi dialog.

### 2. Room Database — CRUD (`30%`)
- **Entity**:
  - [`User_053`](app/src/main/java/com/uts/inventaris053/data/entity/User_053.kt) (`tableName = "user_053"`): `id_053`, `username_053`, `password_053`
  - [`Item_053`](app/src/main/java/com/uts/inventaris053/data/entity/Item_053.kt) (`tableName = "item_053"`): `id_053`, `user_id_053`, `nama_053`, `jumlah_053`, `harga_053`
- **DAO**: [`UserDao_053`](app/src/main/java/com/uts/inventaris053/data/dao/UserDao_053.kt) & [`ItemDao_053`](app/src/main/java/com/uts/inventaris053/data/dao/ItemDao_053.kt)
- **AppDatabase**: [`AppDatabase_053`](app/src/main/java/com/uts/inventaris053/data/database/AppDatabase_053.kt) (`inventaris_database_053`)
- **Operasi CRUD**:
  - **Create**: Menambahkan barang baru milik user yang sedang login (`FormItemActivity_053`).
  - **Read**: Menampilkan daftar barang milik user secara reaktif menggunakan `LiveData`.
  - **Update**: Mengubah nama, jumlah stok, atau harga satuan barang (`FormItemActivity_053`).
  - **Delete**: Menghapus barang dari database dengan dialog konfirmasi hapus.

### 3. UI & RecyclerView (`15%`)
- Menggunakan **`RecyclerView`** ([`ItemAdapter_053`](app/src/main/java/com/uts/inventaris053/ui/adapter/ItemAdapter_053.kt)) dengan kartu Material Design untuk menampilkan nama barang, jumlah unit, harga satuan, serta subtotal nilai per barang.
- Menyediakan tampilan *Empty State* informatif ketika daftar barang masih kosong atau pencarian tidak ditemukan.
- Alur navigasi lengkap: **Login / Register → Dashboard & List Data (RecyclerView) → Form Tambah / Edit → Hapus → Logout**.

### 4. Data Integrity & Validation (`15%`)
- **Isolasi Data Antar User**: Menggunakan `user_id_053` sebagai *Foreign Key* (`onDelete = ForeignKey.CASCADE`) ke tabel `user_053`. Seluruh query barang memfilter `WHERE user_id_053 = :userId_053` sehingga setiap user hanya dapat melihat dan mengelola datanya sendiri.
- **Validasi Input**:
  - Validasi input tidak boleh kosong pada form Login, Register, dan Form Barang.
  - Validasi **password minimal 6 karakter** saat registrasi.
  - Validasi **username unik** (mencegah duplikasi username di database).
  - Validasi jumlah barang (> 0) dan harga barang (>= 0).

### 5. Fitur Bonus (`Max 10%`)
- **Total Nilai Inventaris Real-Time di Dashboard (Bonus Kelompok 4)**: Menampilkan total nilai seluruh inventaris (`SUM(jumlah_053 * harga_053)`), total jenis barang, dan total unit stok secara *real-time* di kartu Dashboard menggunakan `LiveData`.
- **Search Bar Real-Time**: Memfilter dan mencari barang berdasarkan nama secara langsung di halaman Dashboard.
- **ViewBinding**: Menggunakan *ViewBinding* di seluruh Activity dan Adapter (`buildFeatures { viewBinding = true }`).

---

## Struktur Proyek
```text
app/src/main/
├── java/com/uts/inventaris053/
│   ├── data/
│   │   ├── dao/
│   │   │   ├── ItemDao_053.kt          # Query CRUD, Search, & Total Nilai Real-Time
│   │   │   └── UserDao_053.kt          # Query Register, Login, & Cek Username Unik
│   │   ├── database/
│   │   │   └── AppDatabase_053.kt      # Konfigurasi Room Database Singleton
│   │   └── entity/
│   │       ├── Item_053.kt             # Tabel item_053 (dengan ForeignKey user_id_053)
│   │       └── User_053.kt             # Tabel user_053 (dengan Index Unique username_053)
│   ├── ui/
│   │   ├── adapter/
│   │   │   └── ItemAdapter_053.kt      # RecyclerView Adapter dengan ViewBinding
│   │   ├── DashboardActivity_053.kt    # Dashboard Total Nilai Real-Time, Search, & List
│   │   ├── FormItemActivity_053.kt     # Form Tambah & Edit Barang + Preview Subtotal
│   │   ├── LoginActivity_053.kt        # Halaman Login & Pengecekan Sesi
│   │   └── RegisterActivity_053.kt     # Halaman Registrasi & Validasi Akun
│   └── utils/
│       ├── CurrencyHelper_053.kt       # Formatter Rupiah (Rp)
│       └── SessionManager_053.kt       # SharedPreferences Session (user_id_053)
└── res/
    ├── layout/
    │   ├── activity_login_053.xml
    │   ├── activity_register_053.xml
    │   ├── activity_dashboard_053.xml
    │   ├── activity_form_item_053.xml
    │   └── item_inventory_053.xml
    └── values/
        ├── colors.xml
        ├── strings.xml
        └── themes.xml
```

---


