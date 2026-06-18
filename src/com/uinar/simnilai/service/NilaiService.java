package com.uinar.simnilai.service;

import com.uinar.simnilai.dao.NilaiDAO;
import com.uinar.simnilai.model.NilaiMahasiswa;

import java.sql.SQLException;
import java.util.*;

public class NilaiService {

    private final NilaiDAO dao; // DIP: bergantung pada abstraksi

    // Dependency Injection via konstruktor
    public NilaiService(NilaiDAO dao) {
        this.dao = dao;
    }

    public boolean tambahNilai(String nim, String nama, double tugas, double uts, double uas) {
        try {
            if (dao.isNIMExists(nim)) {
                System.out.println("[Service] NIM " + nim + " sudah terdaftar!");
                return false;
            }

            NilaiMahasiswa nm = new NilaiMahasiswa(nim, nama, tugas, uts, uas);
            boolean ok = dao.simpan(nm);

            if (ok) {
                System.out.printf("[Service] Nilai %s (%s) berhasil disimpan.%n", nim, nama);
            }
            return ok;

        } catch (IllegalArgumentException e) {
            System.out.println("[Service] Validasi gagal: " + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.out.println("[Service] Error DB: " + e.getMessage());
            return false;
        }
    }

    public List<NilaiMahasiswa> getDaftarNilai() {
        try {
            return dao.findAll();
        } catch (SQLException e) {
            System.out.println("[Service] Error: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public Optional<NilaiMahasiswa> cariByNIM(String nim) {
        try {
            return dao.findByNIM(nim);
        } catch (SQLException e) {
            System.out.println("[Service] Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public boolean updateNilai(String nim, double tugas, double uts, double uas) {
        try {
            Optional<NilaiMahasiswa> existing = dao.findByNIM(nim);
            if (existing.isEmpty()) {
                System.out.println("[Service] NIM " + nim + " tidak ditemukan.");
                return false;
            }

            NilaiMahasiswa nm = existing.get();
            nm.setNilaiTugas(tugas);
            nm.setNilaiUTS(uts);
            nm.setNilaiUAS(uas);

            return dao.update(nm);

        } catch (IllegalArgumentException e) {
            System.out.println("[Service] Validasi gagal: " + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.out.println("[Service] Error DB: " + e.getMessage());
            return false;
        }
    }

    public boolean hapusNilai(String nim) {
        try {
            return dao.delete(nim);
        } catch (SQLException e) {
            System.out.println("[Service] Error: " + e.getMessage());
            return false;
        }
    }

    public void tampilkanStatistik() {
        try {
            Map<String, Object> stat = dao.getStatistik();

            if (stat.isEmpty() || (int) stat.getOrDefault("totalMahasiswa", 0) == 0) {
                System.out.println("Belum ada data nilai.");
                return;
            }

            System.out.println("\n=== STATISTIK KELAS ===");
            System.out.println("Total Mahasiswa : " + stat.get("totalMahasiswa"));
            System.out.printf("Rata-rata Kelas : %.2f%n", stat.get("rataRata"));
            System.out.printf("Nilai Tertinggi : %.2f%n", stat.get("nilaiTertinggi"));
            System.out.printf("Nilai Terendah  : %.2f%n", stat.get("nilaiTerendah"));
            System.out.println("Distribusi Grade: " + stat.get("distribusiGrade"));

        } catch (SQLException e) {
            System.out.println("[Service] Error statistik: " + e.getMessage());
        }
    }

    public List<NilaiMahasiswa> filterByGrade(String grade) {
        try {
            return dao.findByGrade(grade);
        } catch (SQLException e) {
            System.out.println("[Service] Error: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public void eksporKeCSV(String namaFile) {
        try {
            List<NilaiMahasiswa> daftar = dao.findAll();
            
            if (daftar.isEmpty()) {
                System.out.println("[Service] Tidak ada data untuk diekspor.");
                return;
            }

            // Menggunakan PrintWriter bawaan Java untuk menulis file
            try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.File(namaFile))) {
                // Tulis Header CSV
                writer.println("NIM,Nama,Nilai Tugas,Nilai UTS,Nilai UAS,Nilai Akhir,Grade");
                
                // Tulis Data Baris per Baris
                for (NilaiMahasiswa nm : daftar) {
                    // Nama dibungkus tanda kutip ganda ("") agar aman jika ada nama yang mengandung koma
                    writer.printf("%s,\"%s\",%.2f,%.2f,%.2f,%.2f,%s%n",
                            nm.getNim(),
                            nm.getNama(),
                            nm.getNilaiTugas(),
                            nm.getNilaiUTS(),
                            nm.getNilaiUAS(),
                            nm.getNilaiAkhir(),
                            nm.getGrade());
                }
                System.out.println("[Service] Berhasil! Data telah diekspor ke file: " + namaFile);
                
            } catch (java.io.FileNotFoundException e) {
                System.out.println("[Service] Error: Gagal membuat/menulis file. " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("[Service] Error DB saat ekspor: " + e.getMessage());
        }
    }
}