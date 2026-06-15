# 🎓 SIMNILAI — Sistem Informasi Manajemen Nilai PBO

> Aplikasi berbasis Java dan MySQL untuk mengelola nilai mahasiswa secara cepat, terstruktur, dan otomatis.

Proyek ini merupakan implementasi Praktikum Mata Kuliah **Pemrograman Berorientasi Objek (PBO)** — Program Studi Teknologi Informasi, UIN Ar-Raniry Banda Aceh.

---

## 📋 Daftar Isi

- [Tentang Aplikasi](#-tentang-aplikasi)
- [Fitur](#-fitur)
- [Persyaratan Sistem](#️-persyaratan-sistem)
- [Cara Instalasi](#-cara-instalasi--menjalankan-aplikasi)
- [Cara Penggunaan](#-cara-penggunaan)
- [Struktur Proyek](#-struktur-proyek)
- [Rumus Perhitungan Nilai](#-rumus-perhitungan-nilai)
- [Pemecahan Masalah](#-pemecahan-masalah-troubleshooting)

---

## 📖 Tentang Aplikasi

SIMNILAI (*Sistem Informasi Manajemen Nilai*) hadir sebagai solusi pengganti pengelolaan nilai yang selama ini masih dilakukan manual menggunakan spreadsheet Excel. Masalah yang sering terjadi dengan cara manual:

- ❌ Data mudah hilang atau terhapus tidak sengaja
- ❌ Tidak ada validasi — nilai di luar rentang 0–100 bisa masuk tanpa peringatan
- ❌ Perhitungan nilai akhir harus dilakukan sendiri dan rawan salah hitung
- ❌ Sulit mencari data mahasiswa tertentu dengan cepat

Dengan SIMNILAI, semua masalah itu teratasi secara otomatis.

**Jenis Aplikasi:** Command Line Interface (CLI) — dioperasikan melalui terminal/konsol  
**Bahasa:** Java 17  
**Database:** MySQL 8.0

---

## ✨ Fitur

| No | Fitur | Deskripsi |
|----|-------|-----------|
| 1 | **Tambah Data Nilai** | Input NIM, nama, nilai tugas, UTS, dan UAS dengan validasi otomatis (0–100) |
| 2 | **Lihat Semua Nilai** | Tampilkan seluruh data mahasiswa dalam format tabel yang rapi |
| 3 | **Cari Berdasarkan NIM** | Temukan data satu mahasiswa secara instan |
| 4 | **Update Nilai** | Koreksi nilai mahasiswa yang sudah tersimpan |
| 5 | **Hapus Data** | Hapus data mahasiswa dengan konfirmasi terlebih dahulu |
| 6 | **Statistik Kelas** | Tampilkan rata-rata, nilai tertinggi/terendah, dan distribusi grade (A, B+, B, dst.) |
| 7 | **Filter Berdasarkan Grade** | Tampilkan daftar mahasiswa dengan grade tertentu |

> 💡 **Nilai Akhir dan Grade dihitung otomatis oleh sistem** — tidak perlu menghitung sendiri!

---

## 🛠️ Persyaratan Sistem

Sebelum memulai, pastikan komputer Anda sudah memiliki semua perangkat berikut:

### Wajib Ada

| Perangkat | Versi Minimum | Cara Cek |
|-----------|--------------|----------|
| **Java Development Kit (JDK)** | 17 atau lebih baru | Buka terminal, ketik: `java -version` |
| **MySQL Server** | 8.0 atau lebih baru | Bisa via XAMPP, WAMP, atau instalasi langsung |
| **Git** | Versi berapa pun | Ketik: `git --version` |

### Rekomendasi Editor

Gunakan **Visual Studio Code (VS Code)** dengan ekstensi **Extension Pack for Java** yang sudah terpasang.

### Driver MySQL (Wajib untuk Menjalankan Kode Java)

Unduh file **MySQL Connector/J** versi 8.x dari situs resmi MySQL:  
🔗 https://dev.mysql.com/downloads/connector/j/

---

## 🚀 Cara Instalasi & Menjalankan Aplikasi

Ikuti **5 langkah** berikut secara berurutan. Jangan skip langkah mana pun, ya!

---

### Langkah 1 — Siapkan Database MySQL

> Langkah ini membuat "tempat penyimpanan" data nilai di MySQL.

1. **Nyalakan MySQL** di komputer Anda (aktifkan XAMPP atau jalankan MySQL service).
2. Buka salah satu dari ini:
   - **phpMyAdmin** (buka browser → `http://localhost/phpmyadmin`)
   - **MySQL Workbench**
   - **Terminal / Command Prompt** dengan perintah `mysql -u root -p`
3. **Jalankan script SQL** berikut ini (salin semua, lalu tempel dan jalankan):

```sql
-- Buat database baru
CREATE DATABASE IF NOT EXISTS db_simnilai 
  CHARACTER SET utf8mb4 
  COLLATE utf8mb4_unicode_ci;

-- Pilih database yang baru dibuat
USE db_simnilai;

-- Buat tabel untuk menyimpan data nilai mahasiswa
CREATE TABLE nilai_mahasiswa (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    nim          VARCHAR(15)    NOT NULL,
    nama         VARCHAR(100)   NOT NULL,
    nilai_tugas  DECIMAL(5,2)   NOT NULL DEFAULT 0.00,
    nilai_uts    DECIMAL(5,2)   NOT NULL DEFAULT 0.00,
    nilai_uas    DECIMAL(5,2)   NOT NULL DEFAULT 0.00,

    -- Nilai akhir dan grade dihitung otomatis oleh database
    nilai_akhir  DECIMAL(5,2) GENERATED ALWAYS AS (
                   (nilai_tugas * 0.30) + (nilai_uts * 0.30) + (nilai_uas * 0.40)
                 ) STORED,
    grade        VARCHAR(3) GENERATED ALWAYS AS (
                   CASE
                     WHEN (nilai_tugas*0.30 + nilai_uts*0.30 + nilai_uas*0.40) >= 87 THEN 'A'
                     WHEN (nilai_tugas*0.30 + nilai_uts*0.30 + nilai_uas*0.40) >= 78 THEN 'B+'
                     WHEN (nilai_tugas*0.30 + nilai_uts*0.30 + nilai_uas*0.40) >= 70 THEN 'B'
                     WHEN (nilai_tugas*0.30 + nilai_uts*0.30 + nilai_uas*0.40) >= 63 THEN 'C+'
                     WHEN (nilai_tugas*0.30 + nilai_uts*0.30 + nilai_uas*0.40) >= 56 THEN 'C'
                     WHEN (nilai_tugas*0.30 + nilai_uts*0.30 + nilai_uas*0.40) >= 45 THEN 'D'
                     ELSE 'E'
                   END
                 ) STORED,

    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Validasi: nilai harus antara 0 dan 100
    CONSTRAINT chk_tugas CHECK (nilai_tugas BETWEEN 0 AND 100),
    CONSTRAINT chk_uts   CHECK (nilai_uts   BETWEEN 0 AND 100),
    CONSTRAINT chk_uas   CHECK (nilai_uas   BETWEEN 0 AND 100),

    UNIQUE KEY uk_nim (nim)
);
```

✅ Jika berhasil, database `db_simnilai` dan tabel `nilai_mahasiswa` sudah siap digunakan.

---

### Langkah 2 — Clone Repository

> Langkah ini mengunduh semua kode proyek ke komputer Anda.

Buka **Terminal** (macOS/Linux) atau **Command Prompt / Git Bash** (Windows), lalu jalankan:

```bash
git clone https://github.com/masrulhadi/SimNilai.git
cd SimNilai
```

Setelah ini, folder `SimNilai` berisi semua file proyek.

---

### Langkah 3 — Sesuaikan Password Database

> Langkah ini menghubungkan kode Java ke MySQL di komputer Anda.

1. Di VS Code, buka file:
   ```
   src/com/uinar/simnilai/util/DatabaseConnection.java
   ```
2. Cari baris ini:
   ```java
   private static final String DB_PASS = "password_anda";
   ```
3. Ganti `"password_anda"` dengan password MySQL Anda:
   - Jika menggunakan **XAMPP** dengan pengaturan default → ganti dengan `""` (kosong)
   - Jika punya password → isi dengan password Anda, contoh: `"rahasia123"`

---

### Langkah 4 — Tambahkan Driver MySQL ke Proyek

> Langkah ini agar Java bisa "berbicara" dengan MySQL.

1. Buat folder baru bernama **`lib`** di dalam folder proyek `SimNilai`.
2. Pindahkan file `mysql-connector-j-8.x.x.jar` yang sudah diunduh tadi ke folder `lib`.
3. Di VS Code:
   - Buka panel **Explorer** (ikon folder di sebelah kiri)
   - Gulir ke bawah hingga menemukan bagian **JAVA PROJECTS**
   - Klik ikon **+** di sebelah tulisan **Referenced Libraries**
   - Pilih file `.jar` di dalam folder `lib`

---

### Langkah 5 — Jalankan Aplikasi!

1. Di VS Code, buka file: `src/com/uinar/simnilai/App.java`
2. Klik tombol **▶ Run Java** yang muncul di pojok kanan atas editor
   (atau klik kanan → **Run Java**)
3. Aplikasi SIMNILAI akan muncul di panel **Terminal** bagian bawah VS Code

🎉 Selamat! Aplikasi siap digunakan.

---

## 💻 Cara Penggunaan

Setelah aplikasi berjalan, Anda akan melihat tampilan menu seperti ini:

```
╔══════════════════════════════════════════════════╗
║      SIMNILAI — Sistem Manajemen Nilai PBO       ║
║       Teknologi Informasi | UIN Ar-Raniry        ║
╠══════════════════════════════════════════════════╣
║  1. Tambah Nilai      │  5. Hapus Data           ║
║  2. Lihat Semua       │  6. Statistik Kelas      ║
║  3. Cari by NIM       │  7. Filter by Grade      ║
║  4. Update Nilai      │  0. Keluar               ║
╚══════════════════════════════════════════════════╝
Pilihan (0-7): _
```

Ketik angka yang sesuai dengan menu yang ingin Anda gunakan, lalu tekan **Enter**.

### Contoh: Menambah Nilai Mahasiswa (Menu 1)

```
Pilihan (0-7): 1

── TAMBAH NILAI MAHASISWA ──
NIM            : 190212001
Nama           : Budi Santoso
Nilai Tugas (0-100): 85
Nilai UTS   (0-100): 80
Nilai UAS   (0-100): 90

✓ Nilai 190212001 (Budi Santoso) berhasil disimpan.
  Nilai Akhir: 85.50 | Grade: B+
```

### Contoh: Melihat Semua Nilai (Menu 2)

```
+-----+-------------+---------------------+-------+-------+-------+-------+-------+
| No  | NIM         | Nama                | Tugas | UTS   | UAS   | NA    | Grade |
+-----+-------------+---------------------+-------+-------+-------+-------+-------+
|   1 | 190212001   | Budi Santoso        |  85.0 |  80.0 |  90.0 |  85.5 | B+    |
|   2 | 190212002   | Siti Rahma          |  90.0 |  88.0 |  92.0 |  90.4 | A     |
+-----+-------------+---------------------+-------+-------+-------+-------+-------+
Total: 2 mahasiswa
```

---

## 📁 Struktur Proyek

```
SimNilai/
├── src/
│   └── com/uinar/simnilai/
│       ├── App.java                    ← Titik masuk aplikasi (jalankan ini!)
│       ├── model/
│       │   └── NilaiMahasiswa.java     ← Representasi data satu mahasiswa
│       ├── dao/
│       │   ├── NilaiDAO.java           ← Kontrak operasi database (interface)
│       │   └── NilaiDAOImpl.java       ← Implementasi operasi SQL ke MySQL
│       ├── service/
│       │   └── NilaiService.java       ← Logika bisnis dan validasi input
│       ├── util/
│       │   ├── DatabaseConnection.java ← Pengaturan koneksi ke MySQL
│       │   └── TableFormatter.java     ← Format tampilan tabel di konsol
│       └── ui/
│           └── MenuUtama.java          ← Tampilan menu dan interaksi pengguna
├── lib/
│   └── mysql-connector-j-8.x.x.jar    ← Driver MySQL (tambahkan secara manual)
└── README.md
```

---

## 📊 Rumus Perhitungan Nilai

Nilai Akhir dan Grade dihitung **secara otomatis** menggunakan rumus berikut:

### Rumus Nilai Akhir

```
Nilai Akhir = (Nilai Tugas × 30%) + (Nilai UTS × 30%) + (Nilai UAS × 40%)
```

**Contoh:**  
Tugas = 85, UTS = 80, UAS = 90  
→ Nilai Akhir = (85 × 0.30) + (80 × 0.30) + (90 × 0.40) = 25.5 + 24 + 36 = **85.5**

### Tabel Konversi Grade

| Nilai Akhir | Grade |
|:-----------:|:-----:|
| ≥ 87        | A     |
| 78 – 86.99  | B+    |
| 70 – 77.99  | B     |
| 63 – 69.99  | C+    |
| 56 – 62.99  | C     |
| 45 – 55.99  | D     |
| < 45        | E     |

---

## 🔧 Pemecahan Masalah (Troubleshooting)

### ❌ "Communications link failure" atau "Cannot connect to database"

**Penyebab:** MySQL belum berjalan atau password salah.  
**Solusi:**
1. Pastikan MySQL sudah aktif (cek XAMPP, nyalakan tombol Start di baris MySQL)
2. Periksa kembali password di file `DatabaseConnection.java`
3. Pastikan database `db_simnilai` sudah dibuat (ulangi Langkah 1)

---

### ❌ "ClassNotFoundException: com.mysql.cj.jdbc.Driver"

**Penyebab:** File `.jar` MySQL Connector belum ditambahkan ke proyek.  
**Solusi:** Ulangi **Langkah 4** dan pastikan file `.jar` sudah terdaftar di *Referenced Libraries*.

---

### ❌ "java: error: release version 17 not supported"

**Penyebab:** JDK yang terpasang versinya lebih lama dari 17.  
**Solusi:** Unduh dan instal JDK 17 dari https://adoptium.net, lalu restart VS Code.

---

### ❌ Nilai tidak bisa dimasukkan (error validasi)

**Penyebab:** Nilai yang dimasukkan di luar rentang 0–100, atau NIM mengandung huruf.  
**Solusi:** Pastikan:
- Nilai hanya berisi angka antara 0 dan 100
- NIM hanya berisi digit angka (9–12 digit), tanpa tanda hubung atau huruf

---

## 👥 Kontributor

Proyek ini dikembangkan sebagai bagian dari Praktikum PBO  
**Program Studi Teknologi Informasi — UIN Ar-Raniry Banda Aceh**

---

*Versi 1.0.0 | Juni 2025*