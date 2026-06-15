package com.uinar.simnilai.dao;

import com.uinar.simnilai.model.NilaiMahasiswa;
import com.uinar.simnilai.util.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class NilaiDAOImpl implements NilaiDAO {

    // Helper method untuk mendapatkan koneksi yang aktif
    private Connection getConn() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public boolean isNIMExists(String nim) throws SQLException {
        String sql = "SELECT 1 FROM nilai_mahasiswa WHERE nim = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, nim);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public boolean simpan(NilaiMahasiswa nm) throws SQLException {
        // Kita tidak mengirim id, nilai_akhir, atau grade karena itu di-generate oleh DB
        String sql = "INSERT INTO nilai_mahasiswa (nim, nama, nilai_tugas, nilai_uts, nilai_uas) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, nm.getNim());
            ps.setString(2, nm.getNama());
            ps.setDouble(3, nm.getNilaiTugas());
            ps.setDouble(4, nm.getNilaiUTS());
            ps.setDouble(5, nm.getNilaiUAS());
            
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("[DAO] Simpan berhasil: " + nm.getNim());
                return true;
            }
            return false;
        }
    }

    @Override
    public List<NilaiMahasiswa> findAll() throws SQLException {
        List<NilaiMahasiswa> list = new ArrayList<>();
        String sql = "SELECT * FROM nilai_mahasiswa ORDER BY nim";
        
        try (Statement stmt = getConn().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToEntity(rs));
            }
        }
        return list;
    }

    @Override
    public Optional<NilaiMahasiswa> findByNIM(String nim) throws SQLException {
        String sql = "SELECT * FROM nilai_mahasiswa WHERE nim = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, nim);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToEntity(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<NilaiMahasiswa> findByGrade(String grade) throws SQLException {
        List<NilaiMahasiswa> list = new ArrayList<>();
        String sql = "SELECT * FROM nilai_mahasiswa WHERE grade = ? ORDER BY nim";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, grade);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToEntity(rs));
                }
            }
        }
        return list;
    }

    @Override
    public boolean update(NilaiMahasiswa nm) throws SQLException {
        String sql = "UPDATE nilai_mahasiswa SET nama = ?, nilai_tugas = ?, nilai_uts = ?, nilai_uas = ? WHERE nim = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, nm.getNama());
            ps.setDouble(2, nm.getNilaiTugas());
            ps.setDouble(3, nm.getNilaiUTS());
            ps.setDouble(4, nm.getNilaiUAS());
            ps.setString(5, nm.getNim());
            
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(String nim) throws SQLException {
        String sql = "DELETE FROM nilai_mahasiswa WHERE nim = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, nim);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM nilai_mahasiswa";
        try (Statement stmt = getConn().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    @Override
    public Map<String, Object> getStatistik() throws SQLException {
        Map<String, Object> statistik = new LinkedHashMap<>();

        String sql1 = "SELECT COUNT(*) AS total, AVG(nilai_akhir) AS rata, " +
                      "MAX(nilai_akhir) AS maks, MIN(nilai_akhir) AS min " +
                      "FROM nilai_mahasiswa";

        try (Statement stmt = getConn().createStatement();
             ResultSet rs = stmt.executeQuery(sql1)) {

            if (rs.next()) {
                int total = rs.getInt("total");
                statistik.put("totalMahasiswa", total);

                if (total > 0) { 
                    // PERBAIKAN: hanya isi jika ada data
                    statistik.put("rataRata", Math.round(rs.getDouble("rata")*100.0)/100.0);
                    statistik.put("nilaiTertinggi", rs.getDouble("maks"));
                    statistik.put("nilaiTerendah", rs.getDouble("min"));
                    System.out.println("[DAO] Statistik berhasil dihitung.");
                } else {
                    System.out.println("[DAO] Tabel kosong — statistik tidak tersedia.");
                    return statistik;
                }
            }
        }

        // Query 2: Distribusi grade
        String sql2 = "SELECT grade, COUNT(*) AS jumlah FROM nilai_mahasiswa GROUP BY grade ORDER BY grade";
        Map<String, Integer> distribusi = new LinkedHashMap<>();
        
        // Pre-fill semua grade dengan 0 (Sangat baik untuk menjaga konsistensi UI)
        for (String g : new String[]{"A","B+","B","C+","C","D","E"}) {
            distribusi.put(g, 0);
        }

        try (Statement stmt = getConn().createStatement();
             ResultSet rs = stmt.executeQuery(sql2)) {
            while (rs.next()) {
                distribusi.put(rs.getString("grade"), rs.getInt("jumlah"));
            }
        }

        statistik.put("distribusiGrade", distribusi);
        return statistik;
    }

    // Helper method untuk memetakan ResultSet DB menjadi Objek Java
    private NilaiMahasiswa mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new NilaiMahasiswa(
                rs.getInt("id"),
                rs.getString("nim"),
                rs.getString("nama"),
                rs.getDouble("nilai_tugas"),
                rs.getDouble("nilai_uts"),
                rs.getDouble("nilai_uas"),
                rs.getDouble("nilai_akhir"),
                rs.getString("grade")
        );
    }
}