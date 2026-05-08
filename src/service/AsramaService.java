package service;

import mapper.MahasiswaMapper;
import mapper.PelanggaranMapper;
import model.Mahasiswa;
import model.Pelanggaran;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CLASS: AsramaService
 * =====================
 * Class ini adalah "otak" dari sistem. Berisi semua logika bisnis
 * (business logic) seperti validasi kapasitas kamar, pencarian, dll.
 *
 * Konsep OOP & JCF yang dipakai:
 *   - HashMap<String, Mahasiswa> → menyimpan cache data di memori
 *     (Key = NIM, Value = Object Mahasiswa)
 *   - List<Mahasiswa>            → hasil pencarian/filter
 *   - Encapsulation              → semua logika tersembunyi di service
 *
 * AsramaService berkomunikasi dengan:
 *   - MahasiswaMapper  (untuk operasi DB mahasiswa)
 *   - PelanggaranMapper (untuk operasi DB pelanggaran)
 */
public class AsramaService {

    private static final int MAX_KAPASITAS_KAMAR = 6;

    // === JCF: HashMap sebagai cache mahasiswa di memori ===
    // Key = NIM (String), Value = object Mahasiswa
    private Map<String, Mahasiswa> cache = new HashMap<>();

    // Mapper untuk akses database
    private MahasiswaMapper mahasiswaMapper;
    private PelanggaranMapper pelanggaranMapper;

    // === CONSTRUCTOR ===
    public AsramaService() {
        this.mahasiswaMapper    = new MahasiswaMapper();
        this.pelanggaranMapper  = new PelanggaranMapper();
        muatCacheDariDB(); // muat semua data dari DB ke cache saat program start
    }

    /**
     * Muat semua data mahasiswa dari database ke HashMap (cache).
     * Dipanggil sekali saat program pertama kali dijalankan.
     */
    private void muatCacheDariDB() {
        List<Mahasiswa> list = mahasiswaMapper.findAll();
        for (Mahasiswa m : list) {
            cache.put(m.getNim(), m);
        }
    }

    // ================================================================
    // FITUR 1: TAMBAH MAHASISWA
    // ================================================================
    public String tambahMahasiswa(String nim, String nama, String kamar, int lantai) {
        // Validasi: NIM sudah terdaftar?
        if (cache.containsKey(nim)) {
            return "❌ NIM " + nim + " sudah terdaftar!";
        }

        // Validasi: kamar sudah penuh?
        int jumlahPenghuni = mahasiswaMapper.countByKamar(kamar);
        if (jumlahPenghuni >= MAX_KAPASITAS_KAMAR) {
            return "❌ Kamar " + kamar + " sudah penuh! (Maks " + MAX_KAPASITAS_KAMAR + " orang)";
        }

        // Buat object Mahasiswa baru
        Mahasiswa m = new Mahasiswa(nim, nama, kamar, lantai);

        // Simpan ke database via mapper
        boolean berhasil = mahasiswaMapper.insert(m);
        if (berhasil) {
            cache.put(nim, m); // simpan juga di cache
            return "✅ Mahasiswa " + nama + " berhasil ditambahkan ke kamar " + kamar + "!";
        }
        return "❌ Gagal menambahkan mahasiswa.";
    }

    // ================================================================
    // FITUR 2: TAMPILKAN SEMUA MAHASISWA
    // ================================================================
    public List<Mahasiswa> tampilkanSemua() {
        return mahasiswaMapper.findAll();
    }

    // ================================================================
    // FITUR 3: CARI MAHASISWA BERDASARKAN NIM
    // ================================================================
    public Mahasiswa cariByNim(String nim) {
        // Cek cache dulu (lebih cepat)
        if (cache.containsKey(nim)) {
            return cache.get(nim);
        }
        // Jika tidak ada di cache, cari di database
        return mahasiswaMapper.findByNim(nim);
    }

    // ================================================================
    // FITUR 4 & 5: FILTER / TAMPILKAN ISI KAMAR
    // Input contoh: "k1lt2" → kamar 1 lantai 2
    // ================================================================
    public List<Mahasiswa> tampilkanIsiKamar(String kamar) {
        return mahasiswaMapper.findByKamar(kamar);
    }

    // Cek berapa penghuni saat ini di suatu kamar
    public int getPenghuniKamar(String kamar) {
        return mahasiswaMapper.countByKamar(kamar);
    }

    // ================================================================
    // FITUR 6: TAMBAH DATA PELANGGARAN MAHASISWA
    // ================================================================
    public String tambahPelanggaran(String nim, String deskripsi, int poin) {
        // Cek mahasiswa ada?
        Mahasiswa m = cariByNim(nim);
        if (m == null) {
            return "❌ Mahasiswa dengan NIM " + nim + " tidak ditemukan!";
        }

        // Buat object Pelanggaran
        String tanggal = LocalDate.now().toString(); // format: YYYY-MM-DD
        Pelanggaran p = new Pelanggaran(nim, deskripsi, poin, tanggal);

        // Simpan pelanggaran ke tabel pelanggaran
        boolean berhasil = pelanggaranMapper.insert(p);
        if (!berhasil) return "❌ Gagal menyimpan pelanggaran.";

        // Update poin di tabel mahasiswa
        int poinBaru = m.getPoinPelanggaran() + poin;
        mahasiswaMapper.updatePoin(nim, poinBaru);

        // Update cache juga
        m.setPoinPelanggaran(poinBaru);
        cache.put(nim, m);

        return String.format("✅ Pelanggaran dicatat! %s sekarang punya %d poin.", m.getNama(), poinBaru);
    }

    // ================================================================
    // FITUR 7: TAMPILKAN RIWAYAT PELANGGARAN MAHASISWA
    // ================================================================
    public List<Pelanggaran> getRiwayatPelanggaran(String nim) {
        return pelanggaranMapper.findByNim(nim);
    }

    // ================================================================
    // FITUR 8: TAMPILKAN TOTAL POIN PELANGGARAN
    // ================================================================
    public String getTotalPoin(String nim) {
        Mahasiswa m = cariByNim(nim);
        if (m == null) {
            return "❌ Mahasiswa dengan NIM " + nim + " tidak ditemukan!";
        }
        int total = pelanggaranMapper.getTotalPoin(nim);
        return String.format("📊 Total poin pelanggaran %s (%s): %d poin", m.getNama(), nim, total);
    }

    // ================================================================
    // HAPUS MAHASISWA (bonus)
    // ================================================================
    public String hapusMahasiswa(String nim) {
        if (!cache.containsKey(nim) && mahasiswaMapper.findByNim(nim) == null) {
            return "❌ Mahasiswa dengan NIM " + nim + " tidak ditemukan!";
        }
        boolean berhasil = mahasiswaMapper.delete(nim);
        if (berhasil) {
            cache.remove(nim);
            return "✅ Data mahasiswa NIM " + nim + " berhasil dihapus.";
        }
        return "❌ Gagal menghapus data.";
    }
}
