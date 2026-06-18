package com.uinar.simnilai.ui;

import com.uinar.simnilai.model.NilaiMahasiswa;
import com.uinar.simnilai.service.NilaiService;
import com.uinar.simnilai.util.DatabaseConnection;
import com.uinar.simnilai.util.TableFormatter;
import java.util.*;

public class MenuUtama {

    private final NilaiService service;
    private final Scanner scanner = new Scanner(System.in);

    public MenuUtama(NilaiService service) {
        this.service = service;
    }

    public void jalankan() {
        while (true) {
            tampilkanHeader();
            int pilihan = inputPilihan(0, 8);
            
            switch (pilihan) {
                case 1 -> aksiTambah();
                case 2 -> aksiLihatSemua();
                case 3 -> aksiCariNIM();
                case 4 -> aksiUpdate();
                case 5 -> aksiHapus();
                case 6 -> service.tampilkanStatistik();
                case 7 -> aksiFilterGrade();
                case 8 -> aksiEksporCSV();
                case 0 -> {
                    System.out.println("Terima kasih. Sampai jumpa!");
                    DatabaseConnection.closeAll();
                    return;
                }
            }
            
            System.out.println("\nTekan Enter untuk kembali...");
            scanner.nextLine();
        }
    }

    

    private void tampilkanHeader() {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║       SIMNILAI — Sistem Manajemen Nilai PBO      ║");
        System.out.println("║            Teknologi Informasi | UIN Ar-Raniry   ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║  1. Tambah Nilai        │  5. Hapus Data         ║");
        System.out.println("║  2. Lihat Semua         │  6. Statistik Kelas    ║");
        System.out.println("║  3. Cari by NIM         │  7. Filter by Grade    ║");
        System.out.println("║  4. Update Nilai        │  8. Ekspor ke CSV      ║"); // <-- Tambahan
        System.out.println("║  0. Keluar              │                        ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print(" Pilihan (0-8): "); // <-- Ubah batas atas
    }

    private void aksiTambah() {
        System.out.println("\n── TAMBAH NILAI MAHASISWA ──");
        System.out.print("NIM   : ");
        String nim = scanner.nextLine().trim();
        System.out.print("Nama  : ");
        String nama = scanner.nextLine().trim();
        double tugas = inputNilai("Nilai Tugas");
        double uts = inputNilai("Nilai UTS  ");
        double uas = inputNilai("Nilai UAS  ");

        service.tambahNilai(nim, nama, tugas, uts, uas);
    }

    private void aksiLihatSemua() {
        List<NilaiMahasiswa> daftar = service.getDaftarNilai();
        if (daftar.isEmpty()) {
            System.out.println("Belum ada data nilai.");
            return;
        }
        TableFormatter.printTable(daftar);
    }

    private void aksiCariNIM() {
        System.out.print("\nMasukkan NIM: ");
        String nim = scanner.nextLine().trim();
        service.cariByNIM(nim).ifPresentOrElse(
            TableFormatter::printSingle,
            () -> System.out.println("NIM '" + nim + "' tidak ditemukan.")
        );
    }

    private void aksiUpdate() {
        System.out.print("\nNIM yang akan diupdate: ");
        String nim = scanner.nextLine().trim();
        service.cariByNIM(nim).ifPresentOrElse(existing -> {
            System.out.println("Data saat ini: " + existing);
            double tugas = inputNilai("Nilai Tugas baru");
            double uts = inputNilai("Nilai UTS baru  ");
            double uas = inputNilai("Nilai UAS baru  ");
            service.updateNilai(nim, tugas, uts, uas);
        }, () -> System.out.println("NIM '" + nim + "' tidak ditemukan."));
    }

    private void aksiHapus() {
        System.out.print("\nNIM yang akan dihapus: ");
        String nim = scanner.nextLine().trim();
        System.out.print("Konfirmasi hapus " + nim + "? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            service.hapusNilai(nim);
            System.out.println("Data berhasil dihapus.");
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }

    private void aksiFilterGrade() {
        System.out.print("\nMasukkan grade (A/B+/B/C+/C/D/E): ");
        String grade = scanner.nextLine().trim().toUpperCase();
        List<NilaiMahasiswa> hasil = service.filterByGrade(grade);
        
        if (hasil.isEmpty()) {
            System.out.println("Tidak ada mahasiswa dengan grade " + grade);
        } else {
            System.out.println("Mahasiswa dengan grade " + grade + ":");
            TableFormatter.printTable(hasil);
        }
    }

    private void aksiEksporCSV() {
        System.out.print("\nMasukkan nama file untuk backup (contoh: backup_nilai.csv): ");
        String namaFile = scanner.nextLine().trim();
        
        // Memastikan file diakhiri dengan .csv
        if (!namaFile.toLowerCase().endsWith(".csv")) {
            namaFile += ".csv";
        }
        
        service.eksporKeCSV(namaFile);
    }

    private int inputPilihan(int min, int max) {
        while (true) {
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.print("Masukkan angka " + min + "-" + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Input tidak valid, coba lagi: ");
            }
        }
    }

    private double inputNilai(String label) {
        while (true) {
            System.out.print(label + " (0-100): ");
            try {
                double val = Double.parseDouble(scanner.nextLine().trim());
                if (val >= 0 && val <= 100) return val;
                System.out.println("   Nilai harus antara 0 dan 100!");
            } catch (NumberFormatException e) {
                System.out.println("   Input tidak valid!");
            }
        }
    }
}