import db.DatabaseConnection;
import model.Mahasiswa;
import service.AsramaService;


import java.util.List;
import java.util.Scanner;



/**
 * Entry point program.
 * Tahap 1-2: DB init + model sudah ada.
 * Fitur menu masih sementara untuk tahap berikutnya.
 */
public class Main {

    public static void main(String[] args) {
        DatabaseConnection.getInstance().initDatabase();
        AsramaService service = new AsramaService();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("==============================");
            System.out.println("SISTEM PENDATAAN PENGHUNI ASRAMA");
            System.out.println("1. Tambah mahasiswa");
            System.out.println("2. Tampilkan semua mahasiswa");
            System.out.println("3. Cari mahasiswa berdasarkan NIM");
            System.out.println("4. Filter mahasiswa berdasarkan kamar");
            System.out.println("5. Menampilkan isi kamar (contoh: k1lt2)");
            System.out.println("6. Tambah pelanggaran");
            System.out.println("7. Tampilkan total poin pelanggaran mahasiswa");
            System.out.println("0. Keluar");
            System.out.println("==============================");
            System.out.print("Pilih menu: ");

            String input = sc.nextLine().trim();
            try {
                switch (input) {
                    case "1": {
                        System.out.print("NIM: ");
                        String nim = sc.nextLine().trim();
                        System.out.print("Nama: ");
                        String nama = sc.nextLine().trim();
                        System.out.print("Kamar (angka): ");
                        int kamar = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Lantai (angka): ");
                        int lantai = Integer.parseInt(sc.nextLine().trim());

                        service.tambahMahasiswa(nim, nama, kamar, lantai);
                        System.out.println("Mahasiswa berhasil ditambahkan.");
                        break;
                    }
                    case "2": {
                        System.out.println("--- Daftar semua mahasiswa ---");
                        for (Mahasiswa m : service.tampilSemuaMahasiswa()) {
                            int totalPoin = service.totalPoinByNim(m.getNim());
                            System.out.println(m + " | totalPoin=" + totalPoin);
                        }
                        break;
                    }
                    case "3": {
                        System.out.print("Masukkan NIM: ");
                        String nim = sc.nextLine().trim();
                        Mahasiswa m = service.cariMahasiswaByNim(nim);
                        if (m == null) {
                            System.out.println("Mahasiswa tidak ditemukan.");
                        } else {
                            int totalPoin = service.totalPoinByNim(nim);
                            System.out.println(m + " | totalPoin=" + totalPoin);
                        }
                        break;
                    }
                    case "4": {
                        System.out.print("Kamar (angka): ");
                        int kamar = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Lantai (angka): ");
                        int lantai = Integer.parseInt(sc.nextLine().trim());

                        List<Mahasiswa> list = service.filterByKamar(kamar, lantai);
                        System.out.println("--- Mahasiswa kamar k" + kamar + " lt" + lantai + " ---");
                        if (list.isEmpty()) {
                            System.out.println("Kosong.");
                        } else {
                            for (Mahasiswa m : list) {
                                int totalPoin = service.totalPoinByNim(m.getNim());
                                System.out.println(m + " | totalPoin=" + totalPoin);
                            }
                        }
                        break;
                    }
                    case "5": {
                        System.out.print("Input kamar (format: k{no}lt{no}, contoh k1lt2): ");
                        String s = sc.nextLine().trim().toLowerCase();

                        // parse contoh: k1lt2
                        if (!s.matches("k\\d+lt\\d+")) {
                            System.out.println("Format salah. Contoh yang bener: k1lt2");
                            break;
                        }
                        int kamar = Integer.parseInt(s.substring(1, s.indexOf("lt")));
                        int lantai = Integer.parseInt(s.substring(s.indexOf("lt") + 2));

                        List<Mahasiswa> list = service.filterByKamar(kamar, lantai);
                        System.out.println("--- Daftar mahasiswa di k" + kamar + " lt" + lantai + " ---");
                        if (list.isEmpty()) {
                            System.out.println("Kosong.");
                        } else {
                            for (Mahasiswa m : list) {
                                int totalPoin = service.totalPoinByNim(m.getNim());
                                System.out.println(m + " | totalPoin=" + totalPoin);
                            }
                        }
                        break;
                    }
                    case "6": {
                        System.out.print("NIM mahasiswa: ");
                        String nim = sc.nextLine().trim();
                        System.out.print("Deskripsi pelanggaran: ");
                        String deskripsi = sc.nextLine().trim();
                        System.out.print("Poin pelanggaran (angka): ");
                        int poin = Integer.parseInt(sc.nextLine().trim());

                        service.tambahPelanggaran(nim, deskripsi, poin);
                        System.out.println("Pelanggaran berhasil ditambahkan.");
                        break;
                    }
                    case "7": {
                        System.out.print("Masukkan NIM: ");
                        String nim = sc.nextLine().trim();
                        int total = service.totalPoinByNim(nim);
                        System.out.println("Total poin pelanggaran untuk NIM " + nim + " = " + total);
                        break;
                    }
                    case "0":
                        System.out.println("Selesai. Bye!");
                        return;
                    default:
                        System.out.println("Input tidak valid.");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }


            System.out.println();
        }
    }
}


