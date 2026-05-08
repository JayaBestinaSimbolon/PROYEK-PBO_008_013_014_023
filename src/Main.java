import db.DatabaseConnection;
import model.Mahasiswa;
import model.Pelanggaran;
import service.AsramaService;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 * CLASS: Main (Entry Point)
 * ==========================
 * Titik masuk program. Menampilkan menu CLI dan menerima input user.
 * Semua logika diteruskan ke AsramaService.
 *
 * Alur Program:
 *   1. Inisialisasi koneksi database
 *   2. Tampilkan menu utama
 *   3. Terima input user
 *   4. Panggil method di AsramaService
 *   5. Tampilkan hasil
 *   6. Ulangi sampai user memilih keluar
 */
public class Main {

    private static AsramaService service = new AsramaService();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        // Fix encoding agar karakter tampil benar di terminal Windows
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        tampilkanHeader();

        boolean running = true;
        while (running) {
            tampilkanMenu();
            System.out.print("  Pilih menu: ");
            String pilihan = sc.nextLine().trim();

            switch (pilihan) {
                case "1"  -> menuTambahMahasiswa();
                case "2"  -> menuTampilkanSemua();
                case "3"  -> menuCariByNim();
                case "4"  -> menuTampilkanKamar();
                case "5"  -> menuTambahPelanggaran();
                case "6"  -> menuRiwayatPelanggaran();
                case "7"  -> menuHapusMahasiswa();
                case "0"  -> {
                    System.out.println("\n  👋 Program selesai. Sampai jumpa!\n");
                    running = false;
                }
                default -> System.out.println("  ⚠️  Pilihan tidak valid. Coba lagi.\n");
            }
        }

        DatabaseConnection.closeConnection();
        sc.close();
    }

    // ================================================================
    // MENU HEADER
    // ================================================================
    private static void tampilkanHeader() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("       SISTEM PENDATAAN PENGHUNI ASRAMA");
        System.out.println("       PBO - JDBC | JCF | ORM | Inheritance");
        System.out.println("=".repeat(60) + "\n");
    }

    private static void tampilkanMenu() {
        System.out.println("  +-------------------------------------+");
        System.out.println("  |            MENU UTAMA               |");
        System.out.println("  +-------------------------------------+");
        System.out.println("  |  1. Tambah Mahasiswa                |");
        System.out.println("  |  2. Tampilkan Semua Mahasiswa       |");
        System.out.println("  |  3. Cari Mahasiswa (by NIM)         |");
        System.out.println("  |  4. Tampilkan Isi Kamar             |");
        System.out.println("  |  5. Tambah Pelanggaran              |");
        System.out.println("  |  6. Riwayat & Total Poin            |");
        System.out.println("  |  7. Hapus Mahasiswa                 |");
        System.out.println("  |  0. Keluar                          |");
        System.out.println("  +-------------------------------------+");
    }

    // ================================================================
    // MENU 1: TAMBAH MAHASISWA
    // ================================================================
    private static void menuTambahMahasiswa() {
        System.out.println("\n  === TAMBAH MAHASISWA ===");
        System.out.print("  NIM        : ");
        String nim = sc.nextLine().trim();
        System.out.print("  Nama       : ");
        String nama = sc.nextLine().trim();
        System.out.print("  Kamar (contoh: k1lt2) : ");
        String kamar = sc.nextLine().trim().toLowerCase();
        System.out.print("  Lantai     : ");

        int lantai = 0;
        try {
            lantai = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("  ⚠️  Lantai harus angka!\n");
            return;
        }

        String hasil = service.tambahMahasiswa(nim, nama, kamar, lantai);
        System.out.println("\n  " + hasil + "\n");
    }

    // ================================================================
    // MENU 2: TAMPILKAN SEMUA MAHASISWA
    // ================================================================
    private static void menuTampilkanSemua() {
        System.out.println("\n  === DAFTAR SEMUA MAHASISWA ===");
        List<Mahasiswa> list = service.tampilkanSemua();

        if (list.isEmpty()) {
            System.out.println("  (Belum ada data mahasiswa)\n");
            return;
        }

        System.out.println("  " + "-".repeat(68));
        System.out.printf("  | %-12s | %-20s | %-8s | %-8s | %-7s |%n",
                "NIM", "NAMA", "KAMAR", "LANTAI", "POIN");
        System.out.println("  " + "-".repeat(68));

        for (Mahasiswa m : list) {
            System.out.println("  " + m.getInfo());
        }
        System.out.println("  " + "-".repeat(68));
        System.out.println("  Total: " + list.size() + " mahasiswa\n");
    }

    // ================================================================
    // MENU 3: CARI MAHASISWA BY NIM
    // ================================================================
    private static void menuCariByNim() {
        System.out.println("\n  === CARI MAHASISWA ===");
        System.out.print("  Masukkan NIM: ");
        String nim = sc.nextLine().trim();

        Mahasiswa m = service.cariByNim(nim);
        if (m == null) {
            System.out.println("  ❌ Mahasiswa dengan NIM " + nim + " tidak ditemukan.\n");
        } else {
            System.out.println("\n  Mahasiswa ditemukan:");
            System.out.println("  " + "-".repeat(68));
            System.out.println("  " + m.getInfo());
            System.out.println("  " + "-".repeat(68) + "\n");
        }
    }

    // ================================================================
    // MENU 4: TAMPILKAN ISI KAMAR
    // ================================================================
    private static void menuTampilkanKamar() {
        System.out.println("\n  === TAMPILKAN ISI KAMAR ===");
        System.out.print("  Masukkan kode kamar (contoh: k1lt2): ");
        String kamar = sc.nextLine().trim().toLowerCase();

        List<Mahasiswa> list = service.tampilkanIsiKamar(kamar);
        int jumlah = list.size();

        System.out.println("\n  Kamar  : " + kamar.toUpperCase());
        System.out.println("  Penghuni: " + jumlah + " / 6");
        System.out.println("  " + "-".repeat(68));

        if (list.isEmpty()) {
            System.out.println("  (Kamar kosong atau kode kamar salah)");
        } else {
            System.out.printf("  | %-12s | %-20s | %-8s | %-7s |%n",
                    "NIM", "NAMA", "LANTAI", "POIN");
            System.out.println("  " + "-".repeat(68));
            for (Mahasiswa m : list) {
                System.out.println("  " + m.getInfo());
            }
        }
        System.out.println("  " + "-".repeat(68) + "\n");
    }

    // ================================================================
    // MENU 5: TAMBAH PELANGGARAN
    // ================================================================
    private static void menuTambahPelanggaran() {
        System.out.println("\n  === TAMBAH PELANGGARAN ===");
        System.out.print("  NIM Mahasiswa  : ");
        String nim = sc.nextLine().trim();
        System.out.print("  Deskripsi      : ");
        String deskripsi = sc.nextLine().trim();
        System.out.print("  Poin Pelanggaran: ");

        int poin = 0;
        try {
            poin = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("  ⚠️  Poin harus berupa angka!\n");
            return;
        }

        String hasil = service.tambahPelanggaran(nim, deskripsi, poin);
        System.out.println("\n  " + hasil + "\n");
    }

    // ================================================================
    // MENU 6: RIWAYAT PELANGGARAN & TOTAL POIN
    // ================================================================
    private static void menuRiwayatPelanggaran() {
        System.out.println("\n  === RIWAYAT PELANGGARAN ===");
        System.out.print("  NIM Mahasiswa: ");
        String nim = sc.nextLine().trim();

        // Tampilkan total poin
        System.out.println("\n  " + service.getTotalPoin(nim));

        // Tampilkan riwayat
        List<Pelanggaran> list = service.getRiwayatPelanggaran(nim);
        if (list.isEmpty()) {
            System.out.println("  (Tidak ada riwayat pelanggaran)\n");
            return;
        }

        System.out.println("  " + "-".repeat(68));
        System.out.println("  Riwayat pelanggaran:");
        for (Pelanggaran p : list) {
            System.out.println(p.toString());
        }
        System.out.println("  " + "-".repeat(68) + "\n");
    }

    // ================================================================
    // MENU 7: HAPUS MAHASISWA
    // ================================================================
    private static void menuHapusMahasiswa() {
        System.out.println("\n  === HAPUS MAHASISWA ===");
        System.out.print("  Masukkan NIM mahasiswa yang akan dihapus: ");
        String nim = sc.nextLine().trim();
        System.out.print("  Yakin ingin menghapus? (y/n): ");
        String konfirmasi = sc.nextLine().trim();

        if (konfirmasi.equalsIgnoreCase("y")) {
            String hasil = service.hapusMahasiswa(nim);
            System.out.println("\n  " + hasil + "\n");
        } else {
            System.out.println("  Penghapusan dibatalkan.\n");
        }
    }
}
