# Sistem Pendataan Penghuni Asrama
> Proyek Mini PBO — JDBC | JCF | ORM Data Mapper | Inheritance

**Kelompok:** 008 | 013 | 014 | 023

---

## Konsep OOP yang Diterapkan

| Konsep | File Implementasi |
|--------|--------------------|
| **Class & Object** | `Mahasiswa.java`, `Pelanggaran.java` |
| **Encapsulation** | Private fields + getter/setter di semua model |
| **Inheritance** | `Mahasiswa extends Person` |
| **Abstraction** | `Person` sebagai abstract class |
| **Collection (JCF)** | `HashMap<String, Mahasiswa>` di `AsramaService` |
| **JDBC** | `DatabaseConnection.java` + semua Mapper |
| **ORM Data Mapper** | `MahasiswaMapper.java`, `PelanggaranMapper.java` |

---

## Struktur Folder

```
PROYEK-PBO_008_013_014_023/
├── src/
│   ├── model/
│   │   ├── Person.java           ← Abstract class (parent)
│   │   ├── Mahasiswa.java        ← extends Person (child)
│   │   └── Pelanggaran.java      ← Model pelanggaran
│   ├── db/
│   │   └── DatabaseConnection.java  ← Koneksi JDBC ke SQLite
│   ├── mapper/
│   │   ├── MahasiswaMapper.java     ← ORM Data Mapper mahasiswa
│   │   └── PelanggaranMapper.java   ← ORM Data Mapper pelanggaran
│   ├── service/
│   │   └── AsramaService.java       ← Logika bisnis + JCF
│   └── Main.java                    ← Entry point + menu CLI
├── lib/
│   └── sqlite-jdbc-3.47.1.0.jar    ← Driver SQLite
├── out/                             ← Hasil compile (auto-generated)
├── asrama.db                        ← Database SQLite (auto-generated)
├── run.bat                          ← Script jalankan program (Windows)
└── .vscode/
    ├── tasks.json                   ← Build task VS Code
    └── launch.json                  ← Run config VS Code
```

---

## Cara Menjalankan

### Cara 1 — Double klik (termudah)
```
Double klik file: run.bat
```

### Cara 2 — Terminal VS Code
```bash
# Compile
javac -cp "lib/sqlite-jdbc-3.47.1.0.jar" -d out src/model/Person.java src/model/Mahasiswa.java src/model/Pelanggaran.java src/db/DatabaseConnection.java src/mapper/MahasiswaMapper.java src/mapper/PelanggaranMapper.java src/service/AsramaService.java src/Main.java

# Jalankan
java -cp "out;lib/sqlite-jdbc-3.47.1.0.jar" Main
```

### Cara 3 — VS Code (Ctrl+Shift+B lalu Run)
1. Buka folder proyek di VS Code
2. Tekan `Ctrl+Shift+B` → pilih **Compile Java**
3. Tekan `F5` atau klik tombol Run

---

## Fitur Sistem

| Menu | Fitur |
|------|-------|
| **1** | Tambah mahasiswa (validasi kapasitas kamar maks 6) |
| **2** | Tampilkan semua mahasiswa |
| **3** | Cari mahasiswa berdasarkan NIM |
| **4** | Tampilkan isi kamar (format: k1lt2) |
| **5** | Tambah data pelanggaran mahasiswa |
| **6** | Tampilkan riwayat & total poin pelanggaran |
| **7** | Hapus data mahasiswa |
| **0** | Keluar |

---

## Desain Database SQLite

### Tabel `mahasiswa`
```sql
CREATE TABLE mahasiswa (
    nim              TEXT PRIMARY KEY,
    nama             TEXT NOT NULL,
    kamar            TEXT NOT NULL,
    lantai           INTEGER NOT NULL,
    poin_pelanggaran INTEGER DEFAULT 0
);
```

### Tabel `pelanggaran`
```sql
CREATE TABLE pelanggaran (
    id        INTEGER PRIMARY KEY AUTOINCREMENT,
    nim       TEXT NOT NULL,
    deskripsi TEXT NOT NULL,
    poin      INTEGER NOT NULL,
    tanggal   TEXT NOT NULL,
    FOREIGN KEY (nim) REFERENCES mahasiswa(nim)
);
```

---

## Penjelasan ORM Data Mapper

```
Database (SQLite)
      ↕  ← query SQL (INSERT, SELECT, UPDATE)
  Mapper (MahasiswaMapper / PelanggaranMapper)
      ↕  ← mengubah ResultSet → Object Java
Java Object (Mahasiswa / Pelanggaran)
```

**Keuntungan Data Mapper:**
- Object Java tidak tahu soal database (clean separation)
- Mudah diganti database-nya tanpa mengubah model
- Kode lebih rapi dan mudah ditest

---

## Dependency
- **Java** 17+ (gunakan: `java --version` untuk cek)
- **sqlite-jdbc** 3.47.1.0 (sudah ada di folder `lib/`)
