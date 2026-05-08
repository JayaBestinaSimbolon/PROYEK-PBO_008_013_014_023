package model;

/**
 * CLASS CHILD (Mahasiswa)
 * ========================
 * Mahasiswa mewarisi class Person, artinya Mahasiswa punya semua
 * atribut dari Person (nim, nama) ditambah atribut miliknya sendiri:
 * kamar, lantai, dan poinPelanggaran.
 *
 * Konsep OOP: Inheritance (extends), Encapsulation, Override
 */
public class Mahasiswa extends Person {

    // === ATRIBUT TAMBAHAN MAHASISWA ===
    private String kamar;     // contoh: "k1lt2" (kamar 1 lantai 2)
    private int lantai;
    private int poinPelanggaran;

    // === CONSTRUCTOR ===
    public Mahasiswa(String nim, String nama, String kamar, int lantai, int poinPelanggaran) {
        super(nim, nama); // panggil constructor parent (Person)
        this.kamar = kamar;
        this.lantai = lantai;
        this.poinPelanggaran = poinPelanggaran;
    }

    // Constructor tanpa poin (default poin = 0)
    public Mahasiswa(String nim, String nama, String kamar, int lantai) {
        this(nim, nama, kamar, lantai, 0);
    }

    // === GETTER & SETTER ===
    public String getKamar() {
        return kamar;
    }

    public void setKamar(String kamar) {
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

    public void tambahPoin(int poin) {
        this.poinPelanggaran += poin;
    }

    /**
     * Override method getInfo() dari class Person.
     * Menampilkan semua informasi mahasiswa dalam 1 baris.
     */
    @Override
    public String getInfo() {
        return String.format(
            "| %-12s | %-20s | %-8s | Lantai %-2d | Poin: %-3d |",
            getNim(), getNama(), kamar, lantai, poinPelanggaran
        );
    }
}
