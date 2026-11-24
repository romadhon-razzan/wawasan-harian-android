# Tinjauan Konten Seed (versi konten 1)

Konten ada di `app/src/main/java/id/co/ptn/wawasanharian/data/seed/Stage*.kt`.
Setiap perubahan teks **wajib** menaikkan `SeedCurriculum.VERSION` agar perangkat yang sudah terpasang memuat ulang konten
(progres pengguna tidak terhapus).

## Prinsip yang dipakai
- Hanya konten *evergreen*. Tidak ada nama pejabat, harga, peringkat, atau statistik terbaru.
- Klaim yang bisa berubah atau berbeda antar-sumber ditulis dengan pembatas ("sekitar", "salah satu", "menurut Sensus 2010").
- Sengaja **tidak** dibahas karena sensitif terhadap waktu: status ibu kota negara (proses pemindahan ke Nusantara),
  jumlah anggota ASEAN saat ini, nilai inflasi/suku bunga terkini.

## Klaim yang paling perlu dicek manusia

| Pelajaran | Klaim | Catatan |
|---|---|---|
| s1-l1 | Sekitar 71% permukaan Bumi tertutup air; 7 benua; 5 samudra; Pasifik terbesar & terdalam; Arktik terkecil | Disebut bahwa jumlah benua bisa dihitung berbeda |
| s1-l2 | "Lebih dari enam belas ribu pulau sudah dibakukan namanya" | Angka resmi berubah dari tahun ke tahun; frasa sengaja dibuat longgar |
| s1-l2 | Pendiri ASEAN: Indonesia, Malaysia, Filipina, Singapura, Thailand — 8 Agustus 1967, Bangkok | |
| s1-l2 | Contoh WIB/WITA/WIT; pos lintas batas Entikong | |
| s2-l1 | Aksara paku Sumeria "sekitar 5.000 tahun lalu"; prasasti Yupa (Pallawa, Sanskerta) sebagai awal masa sejarah Indonesia | |
| s2-l2 | Kutai sekitar abad ke-4–5; Sriwijaya berjaya sekitar abad ke-7; Borobudur & Prambanan sekitar abad ke-8–9; Majapahit berdiri 1293; Demak kerajaan Islam pertama di Jawa; Sutasoma karya Mpu Tantular | Penanggalan kerajaan kuno berbeda antar-sumber, sehingga memakai "sekitar" |
| s3-l1 | Kemiringan poros ±23,5°; revolusi ±365¼ hari; musim disebabkan kemiringan poros, bukan jarak | |
| s3-l2 | Fungsi organ; kulit organ terbesar, hati (liver) organ dalam terbesar | Bagian kebiasaan sehat diberi catatan "informasi umum, periksakan ke tenaga kesehatan" |
| s4-l1 | Bank Indonesia sebagai lembaga yang berwenang mengeluarkan rupiah | |
| s4-l2 | BPS mengukur inflasi dengan IHK; BI menjaga inflasi lewat suku bunga acuan | Nama instrumen suku bunga tidak disebut karena pernah berganti |
| s5-l1 | >1.300 kelompok suku menurut Sensus Penduduk 2010; Sumpah Pemuda 28 Oktober 1928; batik diakui UNESCO 2 Oktober 2009 | |
| s5-l2 | Deklarasi Djuanda 1957; UNCLOS 1982; laut teritorial 12 mil laut; ZEE 200 mil laut | |
| s6-l1 | Penjelasan internet vs web; pembelajaran mesin | Tanpa nama produk AI atau klaim pasar |
| s6-l2 | Definisi misinformasi vs disinformasi; 5W+1H | |

## Pemeriksaan otomatis
`SeedCurriculumTest` memastikan: 6 tahap × ≥2 pelajaran, 3–5 bagian penjelasan, 3–5 istilah kunci, ≥2 contoh,
≥2 bahan obrolan, 3–5 soal dengan tepat satu jawaban benar dan penjelasan, teaser widget ≤140 karakter,
serta tidak ada teks kosong atau sisa karakter escape.
