package com.uinar.simnilai.model;

/**
 * Entity class yang merepresentasikan data nilai satu mahasiswa.
 * Memetakan tabel nilai_mahasiswa di database db_simnilai.
 */
public class NilaiMahasiswa {

    private int id;
    private String nim;
    private String nama;
    private double nilaiTugas;
    private double nilaiUTS;
    private double nilaiUAS;
    private double nilaiAkhir; // dihitung, tidak di-set dari luar
    private String grade;      // dihitung, tidak di-set dari luar

    // Konstruktor untuk INSERT baru — dengan validasi
    public NilaiMahasiswa(String nim, String nama, double nilaiTugas, double nilaiUTS, double nilaiUAS) {
        if (nim == null || !nim.matches("\\d{9,12}"))
            throw new IllegalArgumentException("NIM tidak valid: " + nim);
        if (nama == null || nama.isBlank())
            throw new IllegalArgumentException("Nama tidak boleh kosong");

        validateNilai(nilaiTugas, "Tugas");
        validateNilai(nilaiUTS, "UTS");
        validateNilai(nilaiUAS, "UAS");

        this.nim = nim;
        this.nama = nama;
        this.nilaiTugas = nilaiTugas;
        this.nilaiUTS = nilaiUTS;
        this.nilaiUAS = nilaiUAS;

        recalculate();
    }

    // Konstruktor untuk READ dari database (semua field terisi)
    public NilaiMahasiswa(int id, String nim, String nama,
                          double nilaiTugas, double nilaiUTS, double nilaiUAS, 
                          double nilaiAkhir, String grade) {
        this.id = id;
        this.nim = nim;
        this.nama = nama;
        this.nilaiTugas = nilaiTugas;
        this.nilaiUTS = nilaiUTS;
        this.nilaiUAS = nilaiUAS;
        this.nilaiAkhir = nilaiAkhir; // ambil dari DB (sudah dihitung DB)
        this.grade = grade;           // ambil dari DB
    }

    // ── GETTER ────────────────────────────────────────────────────────
    public int getId() { return id; }
    public String getNim() { return nim; }
    public String getNama() { return nama; }
    public double getNilaiTugas() { return nilaiTugas; }
    public double getNilaiUTS() { return nilaiUTS; }
    public double getNilaiUAS() { return nilaiUAS; }
    public double getNilaiAkhir() { return nilaiAkhir; }
    public String getGrade() { return grade; }

    // ── SETTER (hanya untuk field yang boleh diubah) ──────────────────
    public void setNilaiTugas(double nilaiTugas) {
        validateNilai(nilaiTugas, "Tugas");
        this.nilaiTugas = nilaiTugas;
        recalculate();
    }

    public void setNilaiUTS(double nilaiUTS) {
        validateNilai(nilaiUTS, "UTS");
        this.nilaiUTS = nilaiUTS;
        recalculate();
    }

    public void setNilaiUAS(double nilaiUAS) {
        validateNilai(nilaiUAS, "UAS");
        this.nilaiUAS = nilaiUAS;
        recalculate();
    }

    public void setNama(String nama) {
        if (nama == null || nama.isBlank()) 
            throw new IllegalArgumentException("Nama tidak boleh kosong");
        this.nama = nama;
    }

    // ── PRIVATE HELPERS ───────────────────────────────────────────────
    
    // DRY Principle: Kalkulasi dipusatkan agar tidak diulang-ulang
    private void recalculate() {
        this.nilaiAkhir = (nilaiTugas * 0.30) + (nilaiUTS * 0.30) + (nilaiUAS * 0.40);
        this.grade = hitungGrade(this.nilaiAkhir);
    }

    private String hitungGrade(double na) {
        if (na >= 87) return "A";
        if (na >= 78) return "B+";
        if (na >= 70) return "B";
        if (na >= 63) return "C+";
        if (na >= 56) return "C";
        if (na >= 45) return "D";
        return "E";
    }

    private void validateNilai(double nilai, String nama) {
        if (nilai < 0 || nilai > 100)
            throw new IllegalArgumentException("Nilai " + nama + " harus antara 0 dan 100, diterima: " + nilai);
    }

    @Override
    public String toString() {
        return String.format("NilaiMahasiswa{id=%d, nim='%s', nama='%s', tugas=%.1f, uts=%.1f, uas=%.1f, na=%.2f, grade='%s'}",
                id, nim, nama, nilaiTugas, nilaiUTS, nilaiUAS, nilaiAkhir, grade);
    }
}