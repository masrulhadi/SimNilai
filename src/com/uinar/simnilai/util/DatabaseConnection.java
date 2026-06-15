package com.uinar.simnilai.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class untuk mengelola koneksi database MySQL.
 * Memastikan hanya ada satu koneksi yang aktif sepanjang siklus hidup aplikasi.
 */
public class DatabaseConnection {

    // Konfigurasi URL, Username, dan Password database
    private static final String DB_URL = 
        "jdbc:mysql://localhost:3306/db_simnilai?useSSL=false&serverTimezone=Asia/Jakarta";
    private static final String DB_USER = "root";
    
    // Silakan GANTI "password_anda" sesuai dengan konfigurasi password MySQL lokal Anda
    private static final String DB_PASS = ""; 

    private static DatabaseConnection instance;
    private Connection connection;

    // Private Constructor: Mencegah instansiasi objek menggunakan kata kunci 'new' dari luar kelas
    private DatabaseConnection() throws SQLException {
        this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    /**
     * Method global untuk mendapatkan instance tunggal dari DatabaseConnection (Thread-Safe)
     * @return DatabaseConnection instance
     * @throws SQLException jika koneksi gagal
     */
    public static synchronized DatabaseConnection getInstance() throws SQLException {
        if (instance == null || instance.connection.isClosed()) {
            instance = new DatabaseConnection();
            System.out.println("[DB] Koneksi berhasil dibuka ke: " + DB_URL);
        }
        return instance;
    }

    /**
     * Method untuk mendapatkan objek Connection JDBC yang aktif
     * @return Connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Method utilitas untuk menutup koneksi database secara aman saat aplikasi keluar
     */
    public static void closeAll() {
        if (instance == null) return;

        try {
            if (instance.connection != null && !instance.connection.isClosed()) {
                instance.connection.close();
                System.out.println("[DB] Koneksi database berhasil ditutup.");
            }
        } catch (SQLException e) {
            System.err.println("[DB] Error saat menutup koneksi: " + e.getMessage());
        }
    }
}