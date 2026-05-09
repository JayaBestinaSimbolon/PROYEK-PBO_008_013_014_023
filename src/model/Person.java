package model;

/**
 * Person adalah parent class (abstrak) supaya konsep inheritance kebaca.
 * Field disembunyikan (encapsulation).
 */
public abstract class Person {
    private String id;
    private String nama;

    protected Person(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}

