package model;

/**
 * Mahasiswa adalah child class dari Person.
 * Menyimpan data penghuni asrama.
 */
public class Mahasiswa extends Person {
    private String nim; // PK di database
    private int kamar;
    private int lantai;
    private int poinPelanggaran;

    public Mahasiswa(String nim, String nama, int kamar, int lantai) {
        super(nim, nama);
        this.nim = nim;
        this.kamar = kamar;
        this.lantai = lantai;
        this.poinPelanggaran = 0;
    }

    public Mahasiswa(String nim, String nama, int kamar, int lantai, int poinPelanggaran) {
        super(nim, nama);
        this.nim = nim;
        this.kamar = kamar;
        this.lantai = lantai;
        this.poinPelanggaran = poinPelanggaran;
    }

    public String getNim() {
        return nim;
    }

    public int getKamar() {
        return kamar;
    }

    public void setKamar(int kamar) {
        this.kamar = kamar;
    }

    public int getLantai() {
        return lantai;
    }

    public void setLantai(int lantai) {
        this.lantai = lantai;
    }

    public int getPoinPelanggaran() {
        return poinPelanggaran;
    }

    public void setPoinPelanggaran(int poinPelanggaran) {
        this.poinPelanggaran = poinPelanggaran;
    }

    @Override
    public String toString() {
        return "Mahasiswa{" +
                "nim='" + nim + '\'' +
                ", nama='" + getNama() + '\'' +
                ", kamar=" + kamar +
                ", lantai=" + lantai +
                ", poinPelanggaran=" + poinPelanggaran +
                '}';
    }
}

