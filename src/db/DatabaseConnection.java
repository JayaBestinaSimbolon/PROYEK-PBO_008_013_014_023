package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * CLASS: DatabaseConnection
 * ==========================
 * Class ini bertugas mengelola koneksi ke database SQLite.
 * Menggunakan pola Singleton — hanya ada 1 koneksi yang dibuka.
 *
 * Konsep: JDBC (Java Database Connectivity)
 *
 * Alur JDBC:
 *   1. Load driver SQLite
 *   2. Buka koneksi ke file .db
 *   3. Buat tabel jika belum ada
 */
public class DatabaseConnection {

    // Lokasi file database SQLite (otomatis dibuat jika belum ada)
    private static final String DB_URL = "jdbc:sqlite:asrama.db";

    // Satu koneksi yang dipakai sepanjang program berjalan
    private static Connection connection = null;

    /**
     * Mengambil koneksi ke database.
     * Jika belum ada koneksi, buat baru. Jika sudah ada, pakai yang lama.
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
                System.out.println("[DB] Koneksi ke SQLite berhasil.");
                initTabel(connection);
            }
        } catch (SQLException e) {
            System.err.println("[DB ERROR] Gagal koneksi: " + e.getMessage());
        }
        return connection;
    }

    /**
     * Membuat tabel mahasiswa dan pelanggaran jika belum ada.
     * "CREATE TABLE IF NOT EXISTS" = aman dipanggil berkali-kali.
     */
    private static void initTabel(Connection conn) {
        String sqlMahasiswa = """
            CREATE TABLE IF NOT EXISTS mahasiswa (
                nim              TEXT PRIMARY KEY,
                nama             TEXT NOT NULL,
                kamar            TEXT NOT NULL,
                lantai           INTEGER NOT NULL,
                poin_pelanggaran INTEGER DEFAULT 0
            )
            """;

        String sqlPelanggaran = """
            CREATE TABLE IF NOT EXISTS pelanggaran (
                id        INTEGER PRIMARY KEY AUTOINCREMENT,
                nim       TEXT NOT NULL,
                deskripsi TEXT NOT NULL,
                poin      INTEGER NOT NULL,
                tanggal   TEXT NOT NULL,
                FOREIGN KEY (nim) REFERENCES mahasiswa(nim)
            )
            """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sqlMahasiswa);
            stmt.execute(sqlPelanggaran);
            System.out.println("[DB] Tabel siap digunakan.");
        } catch (SQLException e) {
            System.err.println("[DB ERROR] Gagal membuat tabel: " + e.getMessage());
        }
    }

    /**
     * Tutup koneksi database (dipanggil saat program selesai).
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("[DB] Koneksi ditutup.");
            }
        } catch (SQLException e) {
            System.err.println("[DB ERROR] Gagal menutup koneksi: " + e.getMessage());
        }
    }
}
