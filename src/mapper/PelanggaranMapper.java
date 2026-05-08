package mapper;

import db.DatabaseConnection;
import model.Pelanggaran;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CLASS: PelanggaranMapper (ORM Data Mapper)
 * ===========================================
 * Mengurus semua operasi database untuk tabel pelanggaran.
 * Sama seperti MahasiswaMapper — object Pelanggaran tidak
 * tahu soal database, mapper-lah yang mengurus semuanya.
 *
 * Method tersedia:
 *   insert()         → simpan 1 pelanggaran baru
 *   findByNim()      → ambil semua pelanggaran milik 1 mahasiswa
 *   getTotalPoin()   → hitung total poin pelanggaran dari DB
 */
public class PelanggaranMapper {

    private Connection conn;

    public PelanggaranMapper() {
        this.conn = DatabaseConnection.getConnection();
    }

    // ================================================================
    // INSERT — Simpan pelanggaran baru ke database
    // ================================================================
    public boolean insert(Pelanggaran p) {
        String sql = "INSERT INTO pelanggaran (nim, deskripsi, poin, tanggal) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNim());
            ps.setString(2, p.getDeskripsi());
            ps.setInt(3, p.getPoin());
            ps.setString(4, p.getTanggal());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("[Mapper ERROR] insert pelanggaran: " + e.getMessage());
            return false;
        }
    }

    // ================================================================
    // FIND BY NIM — Ambil semua pelanggaran milik satu mahasiswa
    // ================================================================
    public List<Pelanggaran> findByNim(String nim) {
        List<Pelanggaran> list = new ArrayList<>();
        String sql = "SELECT * FROM pelanggaran WHERE nim = ? ORDER BY tanggal DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nim);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("[Mapper ERROR] findByNim pelanggaran: " + e.getMessage());
        }
        return list;
    }

    // ================================================================
    // GET TOTAL POIN — Hitung total poin pelanggaran dari tabel pelanggaran
    // (sebagai verifikasi, karena total juga disimpan di tabel mahasiswa)
    // ================================================================
    public int getTotalPoin(String nim) {
        String sql = "SELECT SUM(poin) FROM pelanggaran WHERE nim = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nim);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("[Mapper ERROR] getTotalPoin: " + e.getMessage());
        }
        return 0;
    }

    // ================================================================
    // HELPER: mapRow — Ubah baris ResultSet → object Pelanggaran
    // ================================================================
    private Pelanggaran mapRow(ResultSet rs) throws SQLException {
        return new Pelanggaran(
            rs.getInt("id"),
            rs.getString("nim"),
            rs.getString("deskripsi"),
            rs.getInt("poin"),
            rs.getString("tanggal")
        );
    }
}
