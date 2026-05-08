package model;

/**
 * CLASS PARENT (Abstract)
 * ========================
 * Person adalah class induk yang menyimpan data dasar seorang individu.
 * Class ini bersifat abstract, artinya tidak bisa dibuat object-nya langsung.
 * Class Mahasiswa akan mewarisi (extends) class ini.
 *
 * Konsep OOP: Inheritance, Encapsulation, Abstraction
 */
public abstract class Person {

    // === ATRIBUT (private = encapsulation) ===
    private String nim;
    private String nama;

    // === CONSTRUCTOR ===
    public Person(String nim, String nama) {
        this.nim = nim;
        this.nama = nama;
    }

    // === GETTER & SETTER ===
    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * Method abstract: wajib di-override oleh class turunan.
     * Fungsinya untuk menampilkan informasi lengkap.
     */
    public abstract String getInfo();

    @Override
    public String toString() {
        return getInfo();
    }
}
