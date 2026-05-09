package service;

import mapper.MahasiswaMapper;
import mapper.PelanggaranMapper;
import model.Mahasiswa;
import model.Pelanggaran;

import java.time.LocalDate;
import java.util.List;

/**
 * AsramaService = logika bisnis (orchestrator) yang pakai mapper.
 * Nanti Main cukup jadi UI/menu, sedangkan service handle proses.
 */
public class AsramaService {

    private final MahasiswaMapper mahasiswaMapper;
    private final PelanggaranMapper pelanggaranMapper;

    public AsramaService() {
        this.mahasiswaMapper = new MahasiswaMapper();
        this.pelanggaranMapper = new PelanggaranMapper();
    }

    // === Tahap 5/6 nanti akan dipakai ===

    public void tambahMahasiswa(String nim, String nama, int kamar, int lantai) {
        // validasi kapasitas kamar max 6
        int count = mahasiswaMapper.countMahasiswaByKamar(kamar, lantai);
        if (count >= 6) {
            throw new RuntimeException("Kamar k" + kamar + " lt" + lantai + " sudah penuh (max 6 mahasiswa). ");
        }

        Mahasiswa m = new Mahasiswa(nim, nama, kamar, lantai);
        mahasiswaMapper.insert(m);
    }

    public List<Mahasiswa> tampilSemuaMahasiswa() {
        return mahasiswaMapper.findAll();
    }

    public Mahasiswa cariMahasiswaByNim(String nim) {
        return mahasiswaMapper.findByNim(nim);
    }

    public List<Mahasiswa> filterByKamar(int kamar, int lantai) {
        return mahasiswaMapper.findByKamar(kamar, lantai);
    }

    public void tambahPelanggaran(String nim, String deskripsi, int poin) {
        String tanggal = LocalDate.now().toString();
        Pelanggaran p = new Pelanggaran(nim, deskripsi, poin, tanggal);
        pelanggaranMapper.insert(p);
    }

    public int totalPoinByNim(String nim) {
        return pelanggaranMapper.getTotalPoinByNim(nim);
    }

    public List<Pelanggaran> riwayatPelanggaranByNim(String nim) {
        return pelanggaranMapper.findAllByNim(nim);
    }
}

