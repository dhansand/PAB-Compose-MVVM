# MVVM and ViewModel in Jetpack Compose

Aplikasi Manajemen Stok Barang (Inventory App) yang dibangun menggunakan **Android Studio**, **Jetpack Compose**, dan **Material 3** dengan menerapkan arsitektur **MVVM (Model-View-ViewModel)** serta **Unidirectional Data Flow (UDF)**.

---

## Identitas Pengembang

- **Nama**: Muhammad Fadhil Aprilino / Sandi Ramadani / Aditya Fajar Aritama
- **NIM**: 24523175 / 24523162 / 24523163
- **Mata Kuliah**: Pengembangan Aplikasi Bergerak (PAB)

---

## Masalah

Pencatatan dan pengelolaan stok barang pada toko kecil atau usaha mikro sering kali masih dilakukan secara manual menggunakan buku catatan fisik atau spreadsheet konvensional. Hal ini menimbulkan beberapa masalah utama:
1. **Human Error**: Rentan terjadi kesalahan pencatatan jumlah stok saat penambahan atau pengurangan barang.
2. **Lambat & Tidak Efisien**: Sulit untuk mengetahui barang mana yang memiliki stok paling sedikit atau paling banyak dengan cepat.
3. **Resiko Kehilangan Data**: Catatan fisik mudah rusak atau hilang, dan spreadsheet kurang fleksibel saat diakses melalui perangkat seluler (*mobile device*).

---

## Pengguna Target

Aplikasi ini dirancang untuk:
- **Pemilik Usaha Mikro, Kecil, dan Menengah (UMKM)** yang memerlukan sistem pencatatan stok sederhana, cepat, dan akurat.
- **Kasir / Pengelola Gudang** yang butuh memantau dan memperbarui ketersediaan stok barang secara *real-time* di perangkat HP maupun Tablet.

---

## Solusi

Aplikasi **Inventory App** hadir sebagai solusi aplikasi *mobile* berbasis Android modern yang:
1. Memilih arsitektur **MVVM (Model-View-ViewModel)** dan **Jetpack Compose** untuk menyajikan antarmuka UI reaktif, cepat, dan bebas dari kendala sinkronisasi state.
2. Menyediakan **input stok dengan stepper interaktif** (`+` / `-`) dan validasi input otomatis untuk mencegah kesalahan data.
3. Menyediakan fitur **filter dan sorting stok otomatis** (stok paling sedikit / paling banyak) untuk membantu pengambilan keputusan restok barang secara cepat.
4. Mendukung **layout adaptif** (Portrait untuk penggunaan satu tangan di HP dan Landscape/Dual-Pane untuk penggunaan di Tablet/Layar Lebar).

---

## Fitur Utama

- **Tambah & Edit Stok Barang**
  - Mengisi nama barang dan jumlah stok.
  - Saat item pada daftar stok diklik, data akan dimuat kembali ke form untuk diedit (judul form berganti menjadi **Edit Barang** dan tombol menjadi **Perbarui**).
- **Stepper & Input Jumlah Stok**
  - Mendukung penambahan/pengurangan stok menggunakan tombol stepper (`+` / `-`) maupun ketik angka langsung.
- **Pengurutan & Filter Stok**
  - Filter **stok paling sedikit** (`LOWEST_FIRST`).
  - Filter **stok paling banyak** (`HIGHEST_FIRST`).
  - Dukungan penekanan ulang (*toggle/uncheck*) chip filter untuk mengembalikan ke urutan semula.
- **Hapus Barang**
  - Menghapus item stok dari daftar dengan aman (otomatis mereset form jika item yang dihapus sedang dalam mode edit).
- **Layout Adaptif & Responsif**
  - **Mode Portrait**: Form input dan header/filter berada secara *fixed* di atas, sedangkan daftar stok dapat di-scroll independen.
  - **Mode Landscape**: Tampilan 2 panel berdampingan (*dual-pane*), di mana panel kiri memuat form input dan panel kanan memuat filter serta daftar stok.
- **Validasi & Empty State Handling**
  - Pesan error interaktif saat nama barang kosong.
  - Tampilan *Empty State* yang ramah pengguna jika daftar stok kosong.

---

## Contoh Alur Unidirectional Data Flow (UDF)

Berikut adalah contoh alur konkrit **Unidirectional Data Flow (UDF)** pada fitur **"Tambah / Edit Barang"**:

```text
  [ User Interaction ]
           │  (e.g., Klik tombol "Tambah" / "Perbarui")
           ▼
  [ UI Event Callback ]  ────►  viewModel.addItem()
                                        │
                                        ▼
                               [ ViewModel Logic ]
                                (Validasi & Update State)
                                        │
                                        ▼
  [ UI Rendering ]       ◄────  [ UI State Emission ]
 (Recomposition via               (StateFlow<InventoryUiState>)
  collectAsStateWithLifecycle)
```

### Langkah Alur Kerja UDF (Step-by-Step):
1. **User Action (UI Event)**:
   Pengguna mengklik tombol **"Tambah"** atau **"Perbarui"** pada komponen `AddItemForm`.
2. **Event Delegation**:
   Event dikirimkan dari UI ke ViewModel melalui lambda callback `onAddItem = viewModel::addItem`.
3. **Business Logic & State Update (ViewModel)**:
   - `InventoryViewModel` mengecek apakah `nameInput` valid (tidak kosong).
   - Jika valid dan dalam mode edit (`isEditing == true`), ViewModel memperbarui item terkait di dalam daftar `items`.
   - Jika tidak dalam mode edit, ViewModel membuat `InventoryItem` baru dan menambahkannya ke daftar `items`.
   - ViewModel memperbarui nilai internal `_uiState.update { ... }` dan mereset input form.
4. **State Emission (StateFlow)**:
   `uiState` baru dipancarkan melalui `StateFlow<InventoryUiState>`.
5. **UI Recomposition**:
   `InventoryScreen` merekam perubahan state via `collectAsStateWithLifecycle()`. Jetpack Compose secara otomatis melakukan *recomposition* pada komponen `AddItemForm` (form kembali bersih) dan `InventoryList` (daftar stok terbaru langsung muncul).

---

## Screenshot Aplikasi

| Mode Portrait (Layar Standar HP) | Mode Landscape (Layar Lebar / Dual-Pane) |
| :---: | :---: |
| ![Mode Portrait]([docs/screenshots/portrait.png](https://github.com/dhansand/PAB-Compose-MVVM/blob/Project/app/src/Cuplikan%20layar%202026-10-29%20005631.png) | ![Mode Landscape]([docs/screenshots/landscape.png](https://github.com/dhansand/PAB-Compose-MVVM/blob/Project/app/src/Cuplikan%20layar%202026-10-29%20005721.png)) |

---

## Struktur Proyek

```text
com.example.myapplication
├── data
│   └── model
│       └── InventoryItem.kt         # Data model untuk barang inventaris (id, name, stock)
├── ui
│   ├── components
│   │   ├── AddItemForm.kt           # Component form input (Tambah / Edit Barang)
│   │   ├── EmptyStateView.kt        # Component tampilan ketika data stok kosong
│   │   ├── InventoryItemCard.kt     # Component kartu item stok (disertai event klik & hapus)
│   │   ├── InventoryList.kt         # Component LazyColumn daftar stok
│   │   └── StockHeaderAndFilter.kt  # Component header daftar stok & chip filter
│   ├── screens
│   │   └── InventoryScreen.kt       # Screen utama dengan dukungan layout Portrait & Landscape
│   ├── state
│   │   └── InventoryUiState.kt      # Immutable UI State (Single Source of Truth)
│   ├── theme
│   │   ├── Color.kt                 # Skema warna aplikasi
│   │   ├── Theme.kt                 # Tema Material 3 aplikasi
│   │   └── Type.kt                  # Konfigurasi tipografi
│   └── viewmodel
│       └── InventoryViewModel.kt    # ViewModel pengelola state reaktif & logika bisnis
└── MainActivity.kt                  # Activity utama pengatur entry point Compose & Edge-to-Edge
```

---

## Cara Menjalankan Aplikasi

1. **Prasyarat**:
   - Android Studio (versi Ladybug / Jellyfish / 2024.2+ direkomendasikan).
   - JDK 17 atau lebih baru.
   - Perangkat Android fisik atau Emulator (Android 7.0 / API 24+).
2. **Langkah-Langkah**:
   1. Clone repositori ini ke komputer lokal Anda:
      ```bash
      git clone https://github.com/dhansand/PAB-Compose-MVVM
      ```
   2. Buka Android Studio, pilih **Open** lalu arahkan ke folder proyek ini.
   3. Tunggu hingga proses **Gradle Sync** selesai secara otomatis.
   4. Pilih konfigurasi run `app` dan klik tombol **Run** (atau tekan `Shift + F10`).

---

## Kontribusi Anggota

| Nama Anggota                          | Peran & Kontribusi Utama |
|:--------------------------------------| :--- |
| **24523175-Muhammad Fadhil Aprilino** | Perancangan Arsitektur MVVM, `InventoryViewModel`, dan `InventoryUiState`. |
| **24523162-Sandi Ramadani**           | Pembuatan Komponen UI Jetpack Compose (`AddItemForm`, `InventoryItemCard`, `StockHeaderAndFilter`). |
| **24523163-Aditya Fajar Aritama**     | Implementasi Layout Adaptif (Portrait & Landscape), penanganan scroll, dan pengujian alur UDF. |
