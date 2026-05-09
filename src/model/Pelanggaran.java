package model;

/**
 * Entity untuk pelanggaran mahasiswa.
 */
public class Pelanggaran {
    private int id;
    private String nim;
    private String deskripsi;
    private int poin;
    private String tanggal; // simpan sebagai String biar simple (diambil dari SQLite)

    public Pelanggaran(String nim, String deskripsi, int poin, String tanggal) {
        this.id = 0;
        this.nim = nim;
        this.deskripsi = deskripsi;
        this.poin = poin;
        this.tanggal = tanggal;
    }

    public Pelanggaran(int id, String nim, String deskripsi, int poin, String tanggal) {
        this.id = id;
        this.nim = nim;
        this.deskripsi = deskripsi;
        this.poin = poin;
        this.tanggal = tanggal;
    }

    public int getId() {
        return id;
    }

    public String getNim() {
        return nim;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public int getPoin() {
        return poin;
    }

    public String getTanggal() {
        return tanggal;
    }

    @Override
    public String toString() {
        return "Pelanggaran{" +
                "id=" + id +
                ", nim='" + nim + '\'' +
                ", deskripsi='" + deskripsi + '\'' +
                ", poin=" + poin +
                ", tanggal='" + tanggal + '\'' +
                '}';
    }
}

