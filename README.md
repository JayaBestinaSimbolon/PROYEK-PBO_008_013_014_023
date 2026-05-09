#Sistem Pendataan Penghuni Asrama

Deskripsi Proyek
Sistem Pendataan Penghuni Asrama merupakan mini proyek PBO berbasis Java yang dibuat untuk membantu pengelolaan data mahasiswa penghuni asrama. Sistem ini dirancang sederhana namun tetap menerapkan konsep penting dalam pemrograman berorientasi objek dan pengelolaan database.

Pada sistem ini, admin dapat menambahkan data penghuni asrama, mencari mahasiswa berdasarkan NIM, melihat isi kamar tertentu, serta mencatat pelanggaran mahasiswa beserta poin pelanggarannya.

Proyek ini dibuat sebagai implementasi dari beberapa materi penting pada mata kuliah Pemrograman Berorientasi Objek (PBO), seperti:
•	JDBC (Java Database Connectivity)
•	Java Collection Framework (JCF)
•	Inheritance
•	ORM dengan pola Data Mapper
•	SQLite Database
 
Latar Belakang
Pendataan penghuni asrama sering kali masih dilakukan secara manual sehingga menyulitkan proses pencarian data mahasiswa, pengecekan kapasitas kamar, maupun pencatatan pelanggaran penghuni.
Melalui proyek ini, kami mencoba membuat sebuah sistem sederhana yang dapat membantu pengelolaan data penghuni asrama secara lebih terstruktur dan mudah digunakan.
Selain itu, proyek ini juga menjadi sarana untuk memahami bagaimana Java dapat diintegrasikan dengan database menggunakan JDBC serta bagaimana data pada database dapat direpresentasikan ke dalam object menggunakan konsep ORM.
 
Fitur Sistem
1. Pendataan Penghuni
Admin dapat menambahkan data mahasiswa penghuni asrama seperti:
•	NIM
•	Nama mahasiswa
•	Kamar
•	Lantai
•	Poin pelanggaran
2. Pencarian Mahasiswa Berdasarkan NIM
Sistem dapat mencari data mahasiswa secara langsung menggunakan NIM.
Contoh:
 
3. Filter dan Pencarian Kamar
Sistem dapat menampilkan seluruh mahasiswa yang berada pada kamar tertentu.
Contoh:
 
4. Pembatasan Kapasitas Kamar
Setiap kamar dibatasi maksimal 6 penghuni.
Jika kamar penuh, maka sistem akan menolak penambahan penghuni baru.
 
5. Pendataan Pelanggaran
Admin dapat mencatat pelanggaran mahasiswa beserta poin pelanggarannya.
Contoh:
•	Terlambat masuk asrama
•	Tidak mengikuti piket
•	Pulang melewati jam malam
 
Teknologi yang Digunakan
	Teknologi	Keterangan
	Java	Bahasa pemrograman utama
	Teknologi	Keterangan
JDBC	Koneksi Java dengan database
SQLite	Database lokal
JCF	Pengelolaan collection data
VS Code	IDE pengembangan
SQLite JDBC Driver	Driver koneksi database
 
Konsep PBO yang Diterapkan
1. Encapsulation
Data mahasiswa disimpan dalam class dan diakses menggunakan getter dan setter.
 
2. Inheritance
Class MahasiswaAsrama merupakan turunan dari class Penghuni.
 
3. ORM Data Mapper
Data pada database diubah menjadi object Java menggunakan class DAO/Data Mapper.
 
4. Java Collection Framework
ArrayList digunakan untuk menyimpan dan mengelola data mahasiswa.
 
Struktur Database
Tabel mahasiswa
	Kolom	Tipe
nim	TEXT
nama	TEXT
kamar	TEXT
lantai	INTEGER
Kolom	Tipe  poin
Tabel pelanggaran
	Kolom	Tipe
id	INTEGER
nim	TEXT
jenis	TEXT
poin	INTEGER
 
Struktur Project
 
Alur Sistem
 
 
Tujuan Proyek
Melalui proyek ini, kami ingin memahami:
•	cara menghubungkan Java dengan database
•	implementasi konsep OOP dalam project nyata
•	penggunaan JDBC dalam pengelolaan data
•	penerapan ORM sederhana menggunakan Data Mapper
•	pengelolaan data menggunakan Java Collection Framework