package com.uinar.simnilai.util;

import com.uinar.simnilai.model.NilaiMahasiswa;
import java.util.List;

public class TableFormatter {

    private static final String SEP = "+---------------------------------------------------------------------------------------+";
    private static final String HDR = "| No | NIM         | Nama                | Tugas |  UTS  |  UAS  |   NA  | Grade |";

    public static void printTable(List<NilaiMahasiswa> list) {
        System.out.println(SEP);
        System.out.println(HDR);
        System.out.println(SEP);

        for (int i = 0; i < list.size(); i++) {
            NilaiMahasiswa m = list.get(i);
            System.out.printf("| %-2d | %-11s | %-19s | %5.1f | %5.1f | %5.1f | %5.2f | %-5s |%n", 
                    i + 1, m.getNim(), m.getNama(),
                    m.getNilaiTugas(), m.getNilaiUTS(), m.getNilaiUAS(), 
                    m.getNilaiAkhir(), m.getGrade());
        }
        System.out.println(SEP);
        System.out.println("Total: " + list.size() + " mahasiswa");
    }

    public static void printSingle(NilaiMahasiswa m) {
        System.out.println("\n┌─ Detail Nilai Mahasiswa ─────────────────────┐");
        System.out.printf ("│ NIM         : %-30s│%n", m.getNim());
        System.out.printf ("│ Nama        : %-30s│%n", m.getNama());
        System.out.printf ("│ Nilai Tugas : %-30.1f│%n", m.getNilaiTugas());
        System.out.printf ("│ Nilai UTS   : %-30.1f│%n", m.getNilaiUTS());
        System.out.printf ("│ Nilai UAS   : %-30.1f│%n", m.getNilaiUAS());
        System.out.printf ("│ Nilai Akhir : %-30.2f│%n", m.getNilaiAkhir());
        System.out.printf ("│ Grade       : %-30s│%n", m.getGrade());
        System.out.println("└──────────────────────────────────────────────┘");
    }
}