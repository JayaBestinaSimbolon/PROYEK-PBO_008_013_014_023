package mapper;

import db.DatabaseConnection;
import model.Mahasiswa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * MahasiswaMapper = Data Mapper untuk entity Mahasiswa.
 * Tujuan: memisahkan logika SQL (mapper) dari logika bisnis (service).
 */
public class MahasiswaMapper {

    public void insert(Mahasiswa m) {
        String sql = "INSERT INTO mahasiswa(nim, nama, kamar, lantai) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNim());
            ps.setString(2, m.getNama());
            ps.setInt(3, m.getKamar());
            ps.setInt(4, m.getLantai());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Gagal insert mahasiswa: " + e.getMessage(), e);
        }
    }

    public Mahasiswa findByNim(String nim) {
        String sql = "SELECT nim, nama, kamar, lantai FROM mahasiswa WHERE nim = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nim);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String n = rs.getString("nama");
                    int kamar = rs.getInt("kamar");
                    int lantai = rs.getInt("lantai");
                    int poin = 0; // poin total dihitung via PelanggaranMapper nanti
                    return new Mahasiswa(nim, n, kamar, lantai, poin);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal findByNim: " + e.getMessage(), e);
        }
        return null;
    }

    public List<Mahasiswa> findAll() {
        String sql = "SELECT nim, nama, kamar, lantai FROM mahasiswa";
        List<Mahasiswa> result = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String nim = rs.getString("nim");
                String nama = rs.getString("nama");
                int kamar = rs.getInt("kamar");
                int lantai = rs.getInt("lantai");
                result.add(new Mahasiswa(nim, nama, kamar, lantai, 0));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal findAll: " + e.getMessage(), e);
        }

        return result;
    }

    public List<Mahasiswa> findByKamar(int kamar, int lantai) {
        String sql = "SELECT nim, nama, kamar, lantai FROM mahasiswa WHERE kamar = ? AND lantai = ?";
        List<Mahasiswa> result = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, kamar);
            ps.setInt(2, lantai);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nim = rs.getString("nim");
                    String nama = rs.getString("nama");
                    result.add(new Mahasiswa(nim, nama, kamar, lantai, 0));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal findByKamar: " + e.getMessage(), e);
        }

        return result;
    }

    public int countMahasiswaByKamar(int kamar, int lantai) {
        String sql = "SELECT COUNT(*) AS total FROM mahasiswa WHERE kamar = ? AND lantai = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, kamar);
            ps.setInt(2, lantai);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal countMahasiswaByKamar: " + e.getMessage(), e);
        }

        return 0;
    }
}

