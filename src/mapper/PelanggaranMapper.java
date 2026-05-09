package mapper;

import db.DatabaseConnection;
import model.Pelanggaran;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * PelanggaranMapper = Data Mapper untuk entity Pelanggaran.
 */
public class PelanggaranMapper {

    public void insert(Pelanggaran p) {
        String sql = "INSERT INTO pelanggaran(nim, deskripsi, poin, tanggal) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNim());
            ps.setString(2, p.getDeskripsi());
            ps.setInt(3, p.getPoin());
            ps.setString(4, p.getTanggal());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Gagal insert pelanggaran: " + e.getMessage(), e);
        }
    }

    public int getTotalPoinByNim(String nim) {
        String sql = "SELECT COALESCE(SUM(poin), 0) AS total FROM pelanggaran WHERE nim = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nim);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal getTotalPoinByNim: " + e.getMessage(), e);
        }

        return 0;
    }

    public List<Pelanggaran> findAllByNim(String nim) {
        String sql = "SELECT id, nim, deskripsi, poin, tanggal FROM pelanggaran WHERE nim = ? ORDER BY id ASC";
        List<Pelanggaran> result = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nim);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String deskripsi = rs.getString("deskripsi");
                    int poin = rs.getInt("poin");
                    String tanggal = rs.getString("tanggal");
                    result.add(new Pelanggaran(id, nim, deskripsi, poin, tanggal));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal findAllByNim: " + e.getMessage(), e);
        }

        return result;
    }
}

