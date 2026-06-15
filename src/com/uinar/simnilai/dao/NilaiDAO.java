package com.uinar.simnilai.dao;

import com.uinar.simnilai.model.NilaiMahasiswa;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface DAO untuk operasi data nilai mahasiswa.
 * Implementasi konkret: NilaiDAOImpl (MySQL via JDBC).
 */
public interface NilaiDAO {

    /** Cek apakah NIM sudah ada di database untuk mencegah duplikasi. */
    boolean isNIMExists(String nim) throws SQLException;

    /** INSERT satu data nilai. @return true jika berhasil */
    boolean simpan(NilaiMahasiswa nm) throws SQLException;

    /** SELECT semua data, diurutkan by nim. */
    List<NilaiMahasiswa> findAll() throws SQLException;

    /** SELECT WHERE nim = ?. @return Optional kosong jika tidak ditemukan */
    Optional<NilaiMahasiswa> findByNIM(String nim) throws SQLException;

    /** SELECT WHERE grade = ?. */
    List<NilaiMahasiswa> findByGrade(String grade) throws SQLException;

    /** UPDATE WHERE nim = ?. @return true jika ada baris yang terupdate */
    boolean update(NilaiMahasiswa nm) throws SQLException;

    /** DELETE WHERE nim = ?. @return true jika ada baris yang terhapus */
    boolean delete(String nim) throws SQLException;

    /** SELECT COUNT(*). */
    int count() throws SQLException;

    /** Hitung statistik kelas: rata-rata, max, min, distribusi grade. */
    Map<String, Object> getStatistik() throws SQLException;
}