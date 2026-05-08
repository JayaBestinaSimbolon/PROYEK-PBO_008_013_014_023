package mapper;

import db.DatabaseConnection;
import model.Mahasiswa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CLASS: MahasiswaMapper (ORM Data Mapper)
 * =========================================
 * Class ini adalah "jembatan" antara object Mahasiswa (Java)
 * dan tabel mahasiswa (SQLite).
 *
 * Pola Data Mapper:
 *   - Object Mahasiswa TIDAK tahu soal database.
 *   - MahasiswaMapper yang mengurus semua query SQL.
 *   - Mapper mengubah ResultSet → Mahasiswa, dan sebaliknya.
 *
 * Method tersedia:
 *   insert()       → simpan mahasiswa baru ke DB
 *   findAll()      → ambil semua mahasiswa dari DB
 *   findByNim()    → cari 1 mahasiswa by NIM
 *   findByKamar()  → ambil daftar mahasiswa by kamar
 *   countByKamar() → hitung jumlah penghuni suatu kamar
 *   updatePoin()   → update poin pelanggaran mahasiswa
 *   delete()       → hapus mahasiswa dari DB
 */
public class MahasiswaMapper {

    private Connection conn;

    public MahasiswaMapper() {
        this.conn = DatabaseConnection.getConnection();
    }

    // ================================================================
    // INSERT — Simpan mahasiswa baru ke database
    // ================================================================
    public boolean insert(Mahasiswa m) {
        String sql = "INSERT INTO mahasiswa (nim, nama, kamar, lantai, poin_pelanggaran) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNim());
            ps.setString(2, m.getNama());
            ps.setString(3, m.getKamar());
            ps.setInt(4, m.getLantai());
            ps.setInt(5, m.getPoinPelanggaran());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("[Mapper ERROR] insert: " + e.getMessage());
            return false;
        }
    }

    // ================================================================
    // FIND ALL — Ambil semua mahasiswa sebagai List
    // ================================================================
    public List<Mahasiswa> findAll() {
        List<Mahasiswa> list = new ArrayList<>();
        String sql = "SELECT * FROM mahasiswa ORDER BY kamar, nim";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRow(rs)); // ubah row → object Mahasiswa
            }
        } catch (SQLException e) {
            System.err.println("[Mapper ERROR] findAll: " + e.getMessage());
        }
        return list;
    }

    // ================================================================
    // FIND BY NIM — Cari satu mahasiswa berdasarkan NIM
    // ================================================================
    public Mahasiswa findByNim(String nim) {
        String sql = "SELECT * FROM mahasiswa WHERE nim = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nim);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("[Mapper ERROR] findByNim: " + e.getMessage());
        }
        return null; // null = tidak ditemukan
    }

    // ================================================================
    // FIND BY KAMAR — Ambil daftar mahasiswa di kamar tertentu
    // ================================================================
    public List<Mahasiswa> findByKamar(String kamar) {
        List<Mahasiswa> list = new ArrayList<>();
        String sql = "SELECT * FROM mahasiswa WHERE kamar = ? ORDER BY nim";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kamar);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("[Mapper ERROR] findByKamar: " + e.getMessage());
        }
        return list;
    }

    // ================================================================
    // COUNT BY KAMAR — Hitung jumlah penghuni suatu kamar
    // ================================================================
    public int countByKamar(String kamar) {
        String sql = "SELECT COUNT(*) FROM mahasiswa WHERE kamar = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kamar);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("[Mapper ERROR] countByKamar: " + e.getMessage());
        }
        return 0;
    }

    // ================================================================
    // UPDATE POIN — Perbarui poin pelanggaran mahasiswa
    // ================================================================
    public boolean updatePoin(String nim, int poinBaru) {
        String sql = "UPDATE mahasiswa SET poin_pelanggaran = ? WHERE nim = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, poinBaru);
            ps.setString(2, nim);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("[Mapper ERROR] updatePoin: " + e.getMessage());
            return false;
        }
    }

    // ================================================================
    // DELETE — Hapus mahasiswa berdasarkan NIM
    // ================================================================
    public boolean delete(String nim) {
        String sql = "DELETE FROM mahasiswa WHERE nim = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nim);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("[Mapper ERROR] delete: " + e.getMessage());
            return false;
        }
    }

    // ================================================================
    // HELPER: mapRow — Mengubah satu baris ResultSet → object Mahasiswa
    // Inilah inti dari pola ORM Data Mapper!
    // ================================================================
    private Mahasiswa mapRow(ResultSet rs) throws SQLException {
        return new Mahasiswa(
            rs.getString("nim"),
            rs.getString("nama"),
            rs.getString("kamar"),
            rs.getInt("lantai"),
            rs.getInt("poin_pelanggaran")
        );
    }
}
