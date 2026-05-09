package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DatabaseConnection bertugas untuk:
 * - menyediakan koneksi JDBC ke SQLite (singleton)
 * - membuat tabel saat aplikasi pertama kali jalan (initDatabase)
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private final String dbUrl;

    private DatabaseConnection() {
        // simpan file db di folder project supaya gampang dilihat
        this.dbUrl = "jdbc:sqlite:asrama.db";
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(dbUrl);
        }
        return connection;
    }

    /**
     * Membuat schema SQLite.
     * Dipanggil saat program start.
     */
    public void initDatabase() {
        String createMahasiswa = "CREATE TABLE IF NOT EXISTS mahasiswa (" +
                "nim TEXT PRIMARY KEY, " +
                "nama TEXT NOT NULL, " +
                "kamar INTEGER NOT NULL, " +
                "lantai INTEGER NOT NULL" +
                ");";

        String createPelanggaran = "CREATE TABLE IF NOT EXISTS pelanggaran (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nim TEXT NOT NULL, " +
                "deskripsi TEXT NOT NULL, " +
                "poin INTEGER NOT NULL, " +
                "tanggal TEXT NOT NULL, " +
                "FOREIGN KEY (nim) REFERENCES mahasiswa(nim) ON DELETE CASCADE" +
                ");";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            // Aktifkan foreign key constraint
            stmt.execute("PRAGMA foreign_keys = ON;");

            stmt.execute(createMahasiswa);
            stmt.execute(createPelanggaran);
        } catch (SQLException e) {
            throw new RuntimeException("Gagal init database: " + e.getMessage(), e);
        }
    }
}

