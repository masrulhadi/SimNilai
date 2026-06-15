```markdown
# 🎓 SIMNILAI - Sistem Informasi Manajemen Nilai PBO

SIMNILAI adalah aplikasi *Command Line Interface* (CLI) berbasis Java dan MySQL yang dirancang untuk memudahkan Dosen dan Asisten Dosen dalam mengelola data nilai mahasiswa secara terstruktur, tervalidasi, dan tersimpan aman di database. 

Proyek ini merupakan implementasi Praktikum Mata Kuliah Pemrograman Berorientasi Objek (PBO) di Program Studi Teknologi Informasi UIN Ar-Raniry.

---

## 🛠️ Persyaratan Sistem (Prerequisites)
Sebelum menjalankan proyek ini, pastikan komputer Anda sudah terinstal:
* **Java Development Kit (JDK)** versi 17 atau lebih baru.
* **MySQL Server** (bisa menggunakan XAMPP, WAMP, atau MySQL Workbench).
* **Git** (untuk mengambil kode dari repository).
* **MySQL Connector/J** (File `.jar` untuk menghubungkan Java ke MySQL).

---

## 🚀 Cara Instalasi & Menjalankan Aplikasi

Ikuti langkah-langkah di bawah ini secara berurutan untuk menjalankan aplikasi di komputer Anda.

### Langkah 1: Siapkan Database MySQL
1. Nyalakan layanan **MySQL** di XAMPP/komputer Anda.
2. Buka MySQL CLI, phpMyAdmin, atau MySQL Workbench.
3. Jalankan *script* SQL berikut untuk membuat database dan tabel secara otomatis:

```sql
CREATE DATABASE IF NOT EXISTS db_simnilai CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE db_simnilai;

CREATE TABLE nilai_mahasiswa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nim VARCHAR(15) NOT NULL UNIQUE,
    nama VARCHAR(100) NOT NULL,
    nilai_tugas DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    nilai_uts DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    nilai_uas DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    nilai_akhir DECIMAL(5,2) GENERATED ALWAYS AS ((nilai_tugas*0.30)+(nilai_uts*0.30)+(nilai_uas*0.40)) STORED,
    grade VARCHAR(3) GENERATED ALWAYS AS (
        CASE 
            WHEN (nilai_tugas*0.30+nilai_uts*0.30+nilai_uas*0.40)>=87 THEN 'A'
            WHEN (nilai_tugas*0.30+nilai_uts*0.30+nilai_uas*0.40)>=78 THEN 'B+'
            WHEN (nilai_tugas*0.30+nilai_uts*0.30+nilai_uas*0.40)>=70 THEN 'B'
            WHEN (nilai_tugas*0.30+nilai_uts*0.30+nilai_uas*0.40)>=63 THEN 'C+'
            WHEN (nilai_tugas*0.30+nilai_uts*0.30+nilai_uas*0.40)>=56 THEN 'C'
            WHEN (nilai_tugas*0.30+nilai_uts*0.30+nilai_uas*0.40)>=45 THEN 'D'
            ELSE 'E' 
        END
    ) STORED,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_tugas CHECK (nilai_tugas BETWEEN 0 AND 100), 
    CONSTRAINT chk_uts CHECK (nilai_uts BETWEEN 0 AND 100), 
    CONSTRAINT chk_uas CHECK (nilai_uas BETWEEN 0 AND 100)
);

```

### Langkah 2: Clone Repository

Buka terminal/Command Prompt di komputer Anda, arahkan ke folder tempat Anda ingin menyimpan proyek ini, lalu jalankan:

```bash
git clone [https://github.com/masrulhadi/SimNilai.git](https://github.com/masrulhadi/SimNilai.git)
cd SimNilai

```

### Langkah 3: Konfigurasi Koneksi Database

Agar Java bisa mengakses database MySQL Anda, sesuaikan *password* di kode sumber:

1. Buka file `src/com/uinar/simnilai/util/DatabaseConnection.java`.
2. Cari baris berikut: `private static final String DB_PASS = "password_anda";`
3. Ubah `"password_anda"` menjadi *password* database MySQL di komputer Anda (Jika menggunakan XAMPP bawaan, biarkan kosong `""`).

### Langkah 4: Tambahkan Driver MySQL Connector (Khusus VS Code)

Karena Java butuh penerjemah untuk bicara dengan MySQL, pastikan file `.jar` telah ditambahkan:

1. Buat folder baru bernama `lib` di dalam folder proyek `SimNilai`.
2. Unduh dan masukkan file `mysql-connector-j-8.x.x.jar` ke dalam folder `lib` tersebut.
3. Di VS Code, buka panel **Explorer**, gulir ke bawah ke menu **JAVA PROJECTS**.
4. Klik tombol **+** di sebelah **Referenced Libraries**.
5. Pilih file `.jar` yang ada di dalam folder `lib` tadi.

### Langkah 5: Jalankan Aplikasi!

1. Buka file `src/com/uinar/simnilai/App.java`.
2. Klik tombol **▶ Run** (atau *Run Java*) yang ada di pojok kanan atas VS Code (atau tepat di atas fungsi `main`).
3. Berinteraksilah dengan menu SIMNILAI melalui jendela **Terminal** di bagian bawah editor Anda.

---

## ✨ Fitur Utama

* **Tambah Data**: Input NIM, Nama, dan Nilai dengan validasi ketat (0-100).
* **Perhitungan Otomatis**: Nilai Akhir (Tugas 30%, UTS 30%, UAS 40%) dan Grade otomatis dihitung oleh sistem.
* **Lihat & Cari Nilai**: Tampilkan seluruh rekap nilai dalam bentuk tabel yang rapi, atau cari berdasarkan NIM.
* **Statistik Kelas**: Ketahui rata-rata nilai, nilai tertinggi, nilai terendah, dan distribusi grade (jumlah A, B, dsb) secara instan.
* **Update & Hapus**: Koreksi nilai yang salah input atau hapus data mahasiswa.

---

*Dibuat untuk keperluan Praktikum PBO.*

```

```
