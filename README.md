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

| Keterangan | Detail |
|---|---|
| **Jenis Aplikasi** | Command Line Interface (CLI) — dioperasikan lewat terminal/konsol |
| **Bahasa Pemrograman** | Java 26 |
| **Database** | MySQL 8.4.3 |
| **Editor yang Direkomendasikan** | Visual Studio Code (VS Code) |

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

Sebelum memulai, pastikan komputer kamu sudah memiliki semua perangkat berikut. Kalau belum ada, ikuti link unduhan yang tersedia.

### 1. Java Development Kit (JDK) 26

Java adalah "mesin" yang menjalankan aplikasi ini. Tanpa JDK, aplikasi tidak bisa berjalan sama sekali.

- 📥 **Unduh JDK 26:** https://adoptium.net/temurin/releases/?version=26
- Pilih sistem operasi kamu (Windows), lalu unduh file `.msi`-nya
- Jalankan installer, klik Next terus hingga selesai

> **Cara cek apakah sudah terpasang:** Buka Command Prompt, ketik `java -version`, lalu tekan Enter.  
> Jika muncul angka versi, berarti sudah terpasang dengan benar. ✅

---

### 2. MySQL Server (via XAMPP atau Laragon)

MySQL adalah tempat data nilai mahasiswa disimpan. Kamu butuh salah satu dari dua pilihan ini:

| | XAMPP | Laragon ⭐ Rekomendasi |
|---|---|---|
| **Kemudahan** | Cukup mudah | Lebih mudah dan ringan |
| **Tampilan** | Sederhana | Modern dan bersih |
| **Cocok untuk** | Pemula umum | Pemula yang ingin lebih nyaman |
| **Unduh** | https://www.apachefriends.org | https://laragon.org/download |

> Kalau belum punya keduanya, **kami merekomendasikan Laragon** karena lebih ringan, lebih cepat dinyalakan, dan tampilannya lebih modern.

---

### 3. Git

Git digunakan untuk mengunduh kode proyek dari GitHub. Kalau kamu lebih nyaman mengunduh file ZIP secara manual, bagian ini bisa dilewati.

- 📥 **Unduh Git untuk Windows:** [Git-2.54.0-64-bit.exe](https://github.com/git-for-windows/git/releases/download/v2.54.0.windows.1/Git-2.54.0-64-bit.exe)
- Jalankan installer, klik Next terus hingga selesai (pengaturan default sudah cukup)

> **Cara cek apakah sudah terpasang:** Buka Command Prompt, ketik `git --version`, lalu tekan Enter. ✅

---

### 4. Visual Studio Code (VS Code)

Editor kode yang akan digunakan untuk membuka dan menjalankan proyek.

- 📥 **Unduh VS Code:** https://code.visualstudio.com
- Setelah terpasang, buka VS Code dan instal ekstensi **Extension Pack for Java**  
  (Klik ikon Extensions di sisi kiri → cari "Extension Pack for Java" → Install)

---

## 🚀 Cara Instalasi & Menjalankan Aplikasi

Ikuti langkah-langkah berikut secara berurutan. Jangan lewati satu pun, ya!

---

### Langkah 1 — Nyalakan MySQL

Pilih sesuai aplikasi yang kamu gunakan:

**Jika menggunakan XAMPP:**
1. Buka aplikasi **XAMPP Control Panel**
2. Klik tombol **Start** di baris **MySQL**
3. Tunggu hingga tulisan MySQL berubah warna menjadi hijau ✅

**Jika menggunakan Laragon:**
1. Buka aplikasi **Laragon**
2. Klik tombol **Start All** (atau **Run All**)
3. Tunggu hingga semua layanan menyala ✅

---

### Langkah 2 — Buat Database lewat Terminal

> Langkah ini membuat "laci penyimpanan" khusus untuk data nilai mahasiswa di dalam MySQL.

**Jika menggunakan XAMPP:**
1. Buka **XAMPP Control Panel**
2. Di baris MySQL, klik tombol **Shell** (kotak hitam kecil di sebelah kanan)
3. Ketik perintah berikut lalu tekan Enter:
   ```
   mysql -u root
   ```

**Jika menggunakan Laragon:**
1. Di jendela Laragon, klik tombol **Terminal**
2. Ketik perintah berikut lalu tekan Enter:
   ```
   mysql -u root
   ```

Setelah masuk ke MySQL (ditandai dengan muncul tanda `mysql>`), **salin seluruh script SQL di bawah ini satu persatu**, tempel ke terminal mysql, lalu tekan Enter:
1. Buat Database
```sql
CREATE DATABASE IF NOT EXISTS db_simnilai 
  CHARACTER SET utf8mb4 
  COLLATE utf8mb4_unicode_ci;
```
2. Masuk/gunakan Database
```sql
USE db_simnilai;
```
3. Buat table
```sql
CREATE TABLE nilai_mahasiswa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nim VARCHAR(15) NOT NULL,
    nama VARCHAR(100) NOT NULL,

    nilai_tugas DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    nilai_uts   DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    nilai_uas   DECIMAL(5,2) NOT NULL DEFAULT 0.00,

    nilai_akhir DECIMAL(5,2) GENERATED ALWAYS AS (
        (nilai_tugas * 0.30) + 
        (nilai_uts   * 0.30) + 
        (nilai_uas   * 0.40)
    ) STORED,

    grade VARCHAR(3) GENERATED ALWAYS AS (
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

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT chk_tugas CHECK (nilai_tugas BETWEEN 0 AND 100),
    CONSTRAINT chk_uts   CHECK (nilai_uts   BETWEEN 0 AND 100),
    CONSTRAINT chk_uas   CHECK (nilai_uas   BETWEEN 0 AND 100),

    UNIQUE KEY uk_nim (nim)
);
```

Jika berhasil, terminal akan menampilkan pesan seperti `Query OK`. ✅  
Database dan tabel sudah siap digunakan.

---

> # <span style="color:red">🚨 MASIH BINGUNG? KLIK LINK INI UNTUK PANDUAN LENGKAP YANG LEBIH MUDAH DIBACA:</span>
> # <span style="color:red">👉 https://github.com/masrulhadi/SimNilai/blob/main/README.md</span>
> # <span style="color:red">Setelah membuka link tersebut, klik ikon COPY yang ada di samping tulisan "Raw" untuk menyalin isinya.</span>
> # <span style="color:red">Lalu paste ke AI favoritmu minta jelasin sama AI. Bilang aja "Bantu aku untuk instalasi ini sampai bisa running!"</span>
> # <span style="color:red">GUNAKAN AI DULU!"</span>

---

### Langkah 3 — Unduh Kode Proyek

Ada dua cara untuk mengunduh proyek ini. Pilih cara yang paling mudah bagimu:

#### Cara A — Download ZIP (Lebih Mudah, Tanpa Git)

1. Buka halaman repository: **https://github.com/masrulhadi/SimNilai**
2. Klik tombol **`< > Code`** berwarna hijau di pojok kanan atas halaman
3. Klik **Download ZIP**
4. Setelah terunduh, **ekstrak** file ZIP tersebut ke lokasi yang mudah ditemukan (misalnya Desktop atau folder Dokumen)

#### Cara B — Clone dengan Git (Jika Git sudah terpasang)

Buka **Command Prompt** atau **Terminal**, lalu jalankan:

```bash
git clone https://github.com/masrulhadi/SimNilai.git
cd SimNilai
```

---

### Langkah 4 — Buka Proyek di VS Code

1. Buka **VS Code**
2. Klik menu **File** → **Open Folder...**
3. Pilih folder `SimNilai` yang baru saja diekstrak/di-clone
4. Klik **Select Folder**

Tunggu sebentar, VS Code akan memuat proyek secara otomatis.

---

### Langkah 5 — Jalankan Aplikasi! 🎉

1. Di panel Explorer VS Code, buka file: `src → com → uinar → simnilai → App.java`
2. Klik tombol **▶ Run Java** yang muncul di pojok **kanan atas** editor  
   (atau klik kanan di area kode → pilih **Run Java**)
3. Aplikasi SIMNILAI akan mulai berjalan di panel **Terminal** bagian bawah VS Code

Jika muncul tampilan menu seperti di bawah ini, berarti aplikasi sudah berjalan dengan sukses! 🎊

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

---

### Kalau Muncul Error Tambahkan Driver MySQL ke Proyek

> Langkah ini agar Java bisa "berbicara" dengan MySQL.

1. Buat folder baru bernama **`lib`** di dalam folder proyek `SimNilai`.
2. Pindahkan file `mysql-connector-j-8.x.x.jar` yang sudah diunduh tadi ke folder `lib`.
3. Di VS Code:
   - Buka panel **Explorer** (ikon folder di sebelah kiri)
   - Gulir ke bawah hingga menemukan bagian **JAVA PROJECTS**
   - Klik ikon **+** di sebelah tulisan **Referenced Libraries**
   - Pilih file `.jar` di dalam folder `lib`
---

## 💻 Cara Penggunaan

Ketik angka menu yang ingin digunakan, lalu tekan **Enter**.

### Menu 1 — Tambah Nilai Mahasiswa

```
Pilihan (0-7): 1

── TAMBAH NILAI MAHASISWA ──
NIM                 : 190212001
Nama                : Budi Santoso
Nilai Tugas (0-100) : 85
Nilai UTS   (0-100) : 80
Nilai UAS   (0-100) : 90

✓ Nilai 190212001 (Budi Santoso) berhasil disimpan.
  Nilai Akhir: 85.50 | Grade: B+
```

### Menu 2 — Lihat Semua Nilai

```
+-----+-------------+---------------------+-------+-------+-------+-------+-------+
| No  | NIM         | Nama                | Tugas | UTS   | UAS   | NA    | Grade |
+-----+-------------+---------------------+-------+-------+-------+-------+-------+
|   1 | 190212001   | Budi Santoso        |  85.0 |  80.0 |  90.0 |  85.5 | B+    |
|   2 | 190212002   | Siti Rahma          |  90.0 |  88.0 |  92.0 |  90.4 | A     |
+-----+-------------+---------------------+-------+-------+-------+-------+-------+
Total: 2 mahasiswa
```

### Menu 6 — Statistik Kelas

```
=== STATISTIK KELAS ===
Total Mahasiswa  : 2
Rata-rata Kelas  : 87.95
Nilai Tertinggi  : 90.40
Nilai Terendah   : 85.50
Distribusi Grade : {A=1, B+=1, B=0, C+=0, C=0, D=0, E=0}
```

### Menu 0 — Keluar

Ketik `0` lalu tekan Enter untuk menutup aplikasi dengan aman.

---

## 📁 Struktur Proyek

```
SimNilai/
├── src/
│   └── com/uinar/simnilai/
│       ├── App.java                    ← 🟢 Jalankan file ini untuk memulai!
│       ├── model/
│       │   └── NilaiMahasiswa.java     ← Data model satu mahasiswa
│       ├── dao/
│       │   ├── NilaiDAO.java           ← Kontrak operasi database (interface)
│       │   └── NilaiDAOImpl.java       ← Implementasi query SQL ke MySQL
│       ├── service/
│       │   └── NilaiService.java       ← Logika bisnis dan validasi input
│       ├── util/
│       │   ├── DatabaseConnection.java ← ⚙️ Konfigurasi koneksi ke MySQL
│       │   └── TableFormatter.java     ← Format tampilan tabel di konsol
│       └── ui/
│           └── MenuUtama.java          ← Tampilan menu dan interaksi pengguna
├── lib/
│   └── mysql-connector-j-8.x.x.jar    ← Driver MySQL
└── README.md
```

---

> # <span style="color:red">🚨 MASIH BINGUNG? KLIK LINK INI UNTUK PANDUAN LENGKAP YANG LEBIH MUDAH DIBACA:</span>
> # <span style="color:red">👉 https://github.com/masrulhadi/SimNilai/blob/main/README.md</span>
> # <span style="color:red">Setelah membuka link tersebut, klik ikon COPY yang ada di samping tulisan "Raw" untuk menyalin isinya.</span>
> # <span style="color:red">Lalu paste ke AI favoritmu minta jelasin sama AI. Bilang aja "Bantu aku untuk instalasi ini sampai bisa running!"</span>
> # <span style="color:red">GUNAKAN AI DULU!"</span>

---

## 📊 Rumus Perhitungan Nilai

Nilai Akhir dan Grade dihitung **secara otomatis** — kamu tidak perlu menghitung sendiri.

### Rumus Nilai Akhir

```
Nilai Akhir = (Nilai Tugas × 30%) + (Nilai UTS × 30%) + (Nilai UAS × 40%)
```

**Contoh:**  
Tugas = 85, UTS = 80, UAS = 90  
→ (85 × 0.30) + (80 × 0.30) + (90 × 0.40) = 25.5 + 24 + 36 = **85.5 → Grade B+**

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
1. Pastikan XAMPP/Laragon sudah dinyalakan dan MySQL sudah aktif
2. Periksa kembali password di file `DatabaseConnection.java` (Langkah 5)
3. Pastikan database `db_simnilai` sudah dibuat (ulangi Langkah 2)

---

### ❌ "ClassNotFoundException: com.mysql.cj.jdbc.Driver"

**Penyebab:** File driver `.jar` MySQL Connector belum terdaftar di proyek.  
**Solusi:**
1. Pastikan file `mysql-connector-j-8.x.x.jar` ada di dalam folder `lib`
2. Di VS Code, buka panel **JAVA PROJECTS** → klik **+** di **Referenced Libraries** → pilih file `.jar` tersebut

---

### ❌ "java: error: release version not supported"

**Penyebab:** Versi JDK yang terpasang tidak sesuai.  
**Solusi:** Unduh dan instal ulang JDK 26 dari https://adoptium.net, lalu restart VS Code.

---

### ❌ Nilai tidak bisa dimasukkan / muncul pesan error validasi

**Penyebab:** Format input tidak sesuai.  
**Solusi:** Pastikan:
- Nilai hanya berisi angka antara **0 sampai 100** (tidak boleh lebih atau kurang)
- NIM hanya berisi **digit angka** (9–12 digit), tanpa huruf atau tanda hubung

---

### ❌ Tombol "Run Java" tidak muncul di VS Code

**Penyebab:** Ekstensi Java belum terpasang di VS Code.  
**Solusi:**
1. Klik ikon **Extensions** di sisi kiri VS Code (ikon kotak-kotak)
2. Cari **"Extension Pack for Java"**
3. Klik **Install**, tunggu hingga selesai, lalu restart VS Code

---

## 👥 Kontributor

Proyek ini dikembangkan sebagai bagian dari Praktikum PBO  
**Program Studi Teknologi Informasi — UIN Ar-Raniry Banda Aceh**

---

*Versi 1.0.0 | Juni 2025*

---

> # <span style="color:red">🚨 MASIH BINGUNG? KLIK LINK INI UNTUK PANDUAN LENGKAP YANG LEBIH MUDAH DIBACA:</span>
> # <span style="color:red">👉 https://github.com/masrulhadi/SimNilai/blob/main/README.md</span>
> # <span style="color:red">Setelah membuka link tersebut, klik ikon COPY yang ada di samping tulisan "Raw" untuk menyalin isinya.</span>
> # <span style="color:red">Lalu paste ke AI favoritmu minta jelasin sama AI. Bilang aja "Bantu aku untuk instalasi ini sampai bisa running!"</span>
> # <span style="color:red">GUNAKAN AI DULU!"</span>
