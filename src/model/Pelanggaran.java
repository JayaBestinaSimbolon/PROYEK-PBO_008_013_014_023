package model;

/**
 * CLASS MODEL: Pelanggaran
 * =========================
 * Class ini merepresentasikan satu record pelanggaran mahasiswa.
 * Setiap kali mahasiswa melanggar, data disimpan di tabel pelanggaran.
 *
 * Konsep OOP: Encapsulation, Class & Object
 */
public class Pelanggaran {

    // === ATRIBUT ===
    private int id;
    private String nim;          // NIM mahasiswa yang melanggar
    private String deskripsi;    // contoh: "Pulang lewat jam malam"
    private int poin;            // contoh: 10
    private String tanggal;      // format: YYYY-MM-DD

    // === CONSTRUCTOR (untuk insert baru — id auto dari DB) ===
    public Pelanggaran(String nim, String deskripsi, int poin, String tanggal) {
        this.nim = nim;
        this.deskripsi = deskripsi;
        this.poin = poin;
        this.tanggal = tanggal;
    }

    // Constructor lengkap (untuk load dari DB yang sudah ada id-nya)
    public Pelanggaran(int id, String nim, String deskripsi, int poin, String tanggal) {
        this.id = id;
        this.nim = nim;
        this.deskripsi = deskripsi;
        this.poin = poin;
        this.tanggal = tanggal;
    }

    // === GETTER & SETTER ===
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public int getPoin() { return poin; }
    public void setPoin(int poin) { this.poin = poin; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }

    @Override
    public String toString() {
        return String.format(
            "  [%d] %s | Poin: %d | Tanggal: %s | Ket: %s",
            id, nim, poin, tanggal, deskripsi
        );
    }
}
