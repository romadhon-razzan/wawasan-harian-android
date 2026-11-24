package id.co.ptn.wawasanharian.data.seed

internal val stageDuniaModern = SeedStage(
    id = "s6",
    title = "Dunia Modern dan Isu Terkini",
    description = "Memahami teknologi digital dan cara cerdas menyikapi informasi, agar bisa mengikuti obrolan tentang isu terkini.",
    lessons = listOf(
        SeedLesson(
            id = "s6-l1",
            title = "Apa itu internet, komputer, dan kecerdasan buatan?",
            summary = "Memahami cara kerja dasar komputer, internet, dan kecerdasan buatan (AI), termasuk keterbatasannya.",
            widgetTeaser = "Internet dan web itu berbeda: internet adalah jaringannya, web adalah salah satu layanan yang berjalan di atasnya.",
            sections = listOf(
                kenali(
                    "Komputer: menerima, mengolah, menghasilkan",
                    """
                    Komputer adalah mesin yang menerima data (input), mengolahnya (proses), menyimpannya, lalu menghasilkan keluaran (output). Ponselmu pun sebenarnya sebuah komputer kecil.

                    Perangkat keras (hardware) adalah bagian fisiknya, seperti layar dan prosesor. Perangkat lunak (software) adalah program yang memberi perintah, misalnya aplikasi pesan. Prosesor (CPU) bertugas menjalankan perintah-perintah itu, sehingga sering disebut "otak" komputer.
                    """,
                ),
                ketahui(
                    "Internet dan web",
                    """
                    Internet adalah jaringan raksasa yang menghubungkan miliaran perangkat di seluruh dunia. Data yang dikirim—misalnya sebuah foto—dipecah menjadi paket-paket kecil, dikirim lewat berbagai jalur, lalu disusun kembali di tujuan.

                    Web (World Wide Web) adalah salah satu layanan yang berjalan di atas internet: kumpulan halaman yang kita buka lewat peramban (browser). Layanan lain di internet misalnya surel (email) dan panggilan video. Jadi, internet adalah jalannya, sedangkan web adalah salah satu kendaraan yang lewat.
                    """,
                ),
                ketahui(
                    "Kecerdasan buatan (AI)",
                    """
                    Kecerdasan buatan atau AI (artificial intelligence) adalah teknologi yang membuat komputer mampu melakukan tugas yang biasanya membutuhkan kecerdasan manusia, seperti mengenali wajah, menerjemahkan bahasa, atau menjawab pertanyaan.

                    Banyak AI modern bekerja dengan pembelajaran mesin (machine learning): komputer mempelajari pola dari banyak contoh data, bukan diprogram aturannya satu per satu. Karena belajar dari pola, AI bisa salah—bahkan bisa menyampaikan informasi keliru dengan nada yakin. AI juga bisa membawa bias dari data yang dipelajarinya.
                    """,
                ),
                hubungkan(
                    "AI di sekitar kita",
                    """
                    Kamu mungkin sudah sering memakai AI tanpa sadar: rekomendasi video di media sosial, terjemahan otomatis, pengenalan wajah untuk membuka kunci ponsel, hingga asisten yang bisa diajak bercakap.

                    Tips sederhana: anggap jawaban AI sebagai bahan awal, bukan kebenaran mutlak. Periksa kembali informasi penting—terutama soal kesehatan, hukum, dan keuangan—ke sumber tepercaya, dan jangan membagikan data pribadi sembarangan.
                    """,
                ),
                rangkum(
                    """
                    • Komputer menerima input, memprosesnya, menyimpan, dan menghasilkan output; CPU menjalankan perintah.
                    • Internet adalah jaringan; web adalah salah satu layanan di atasnya.
                    • AI modern banyak memakai pembelajaran mesin: belajar pola dari data.
                    • AI bisa keliru dan bias, jadi periksa ulang informasi penting.
                    """,
                ),
            ),
            keyTerms = listOf(
                term("Perangkat keras (hardware)", "Bagian fisik komputer, seperti layar, prosesor, dan memori."),
                term("Perangkat lunak (software)", "Program yang memberi perintah kepada komputer, seperti aplikasi."),
                term("Internet", "Jaringan global yang menghubungkan perangkat-perangkat di seluruh dunia."),
                term("Pembelajaran mesin", "Cara membuat komputer mempelajari pola dari contoh data, bukan dari aturan yang ditulis satu per satu."),
            ),
            examples = listOf(
                "Saat video di media sosial terasa pas dengan minatmu, itu hasil sistem rekomendasi yang mempelajari kebiasaan menontonmu.",
                "Ketika kamu mengirim foto lewat aplikasi pesan, foto itu dipecah menjadi paket data, dikirim lewat internet, lalu disusun kembali di ponsel penerima.",
            ),
            prompts = listOf(
                prompt(
                    "Teman berkata, \"Kata AI begini, pasti benar.\" Bagaimana menanggapinya dengan santai?",
                    "AI memang membantu, tapi ia belajar dari pola data, jadi bisa saja keliru. Untuk hal penting, sebaiknya kita cek dulu ke sumber tepercaya.",
                ),
                prompt(
                    "Bagaimana menjelaskan perbedaan internet dan web kepada orang tua?",
                    "Internet itu jaringan yang menghubungkan perangkat di seluruh dunia—seperti jalan raya. Web itu halaman-halaman yang kita buka di browser, salah satu hal yang lewat di jalan itu.",
                ),
            ),
            quiz = listOf(
                question(
                    "Apa hubungan internet dan web?",
                    listOf(
                        "Keduanya sama persis",
                        "Web adalah salah satu layanan yang berjalan di atas internet",
                        "Internet adalah bagian dari web",
                        "Web bisa dibuka tanpa jaringan apa pun",
                    ),
                    1,
                    "Internet adalah jaringannya. Web adalah salah satu layanan di atasnya, sama seperti email dan panggilan video.",
                ),
                question(
                    "Bagian komputer yang menjalankan perintah program disebut…",
                    listOf("Prosesor (CPU)", "Layar", "Papan ketik", "Kabel jaringan"),
                    0,
                    "Prosesor atau CPU menjalankan perintah-perintah program, sehingga sering disebut otak komputer.",
                ),
                question(
                    "Pembelajaran mesin (machine learning) bekerja dengan cara…",
                    listOf(
                        "Menghafal semua jawaban yang ditulis manusia",
                        "Bekerja tanpa data sama sekali",
                        "Mempelajari pola dari banyak contoh data",
                        "Memilih jawaban secara acak",
                    ),
                    2,
                    "Pembelajaran mesin membuat komputer mempelajari pola dari banyak contoh data. Karena itu hasilnya bisa keliru jika polanya menyesatkan.",
                ),
                question(
                    "Sikap yang tepat terhadap jawaban AI tentang hal penting adalah…",
                    listOf(
                        "Langsung mempercayainya",
                        "Mengabaikan semua jawaban AI",
                        "Segera membagikannya ke grup",
                        "Memeriksa ulang ke sumber tepercaya",
                    ),
                    3,
                    "AI bisa membantu, tetapi juga bisa keliru. Untuk hal penting, periksa kembali ke sumber yang tepercaya.",
                ),
            ),
        ),
        SeedLesson(
            id = "s6-l2",
            title = "Bagaimana cara memahami berita dan membedakan fakta dari opini?",
            summary = "Belajar membaca berita dengan cermat: unsur 5W+1H, perbedaan fakta dan opini, serta cara mengenali hoaks.",
            widgetTeaser = "Fakta bisa dibuktikan kebenarannya; opini adalah pendapat atau penilaian seseorang.",
            sections = listOf(
                kenali(
                    "Fakta dan opini",
                    """
                    Fakta adalah informasi yang bisa dibuktikan kebenarannya, misalnya "Jembatan itu diresmikan pada bulan Mei." Kita bisa mengeceknya dari dokumen atau saksi.

                    Opini adalah pendapat, perasaan, atau penilaian seseorang, misalnya "Jembatan itu sangat indah." Opini tidak salah, tetapi tidak bisa dibuktikan benar atau salah seperti fakta.

                    Kata-kata yang sering menandai opini antara lain: menurut saya, sebaiknya, seharusnya, paling, indah, dan buruk.
                    """,
                ),
                ketahui(
                    "Membaca berita dengan 5W+1H",
                    """
                    Berita yang baik menjawab enam pertanyaan dasar:

                    • What (apa) yang terjadi?
                    • Who (siapa) yang terlibat?
                    • When (kapan) terjadinya?
                    • Where (di mana) terjadinya?
                    • Why (mengapa) itu terjadi?
                    • How (bagaimana) kejadiannya?

                    Jika sebuah informasi tidak jelas sumber, waktu, atau tempatnya, kita patut lebih berhati-hati.
                    """,
                ),
                ketahui(
                    "Mengenali hoaks",
                    """
                    Hoaks adalah informasi bohong yang disebarkan seolah-olah benar. Ada dua istilah yang perlu dibedakan:

                    • Misinformasi: informasi keliru yang disebarkan tanpa niat menipu—misalnya karena penyebarnya juga percaya.
                    • Disinformasi: informasi keliru yang sengaja dibuat atau disebarkan untuk menipu.

                    Ciri yang patut dicurigai: judul yang memancing emosi atau terlalu bombastis, ajakan "sebarkan sebelum dihapus!", tidak ada sumber yang jelas, serta foto atau video lama yang dipakai untuk kejadian baru.
                    """,
                ),
                hubungkan(
                    "Sebelum membagikan, periksa dulu",
                    """
                    Sebelum meneruskan pesan di grup keluarga atau media sosial, coba langkah sederhana ini:

                    1. Baca isinya sampai habis, bukan hanya judulnya.
                    2. Cek sumbernya: apakah dari media atau lembaga resmi yang jelas?
                    3. Lihat tanggalnya: apakah ini berita lama yang diedarkan ulang?
                    4. Bandingkan dengan media lain yang tepercaya.
                    5. Gunakan situs atau kanal cek fakta jika ragu.

                    Jika belum yakin, lebih baik tidak dibagikan.
                    """,
                ),
                rangkum(
                    """
                    • Fakta bisa dibuktikan; opini adalah pendapat atau penilaian.
                    • Berita yang baik menjawab 5W+1H: apa, siapa, kapan, di mana, mengapa, dan bagaimana.
                    • Misinformasi tidak disengaja; disinformasi sengaja menipu.
                    • Baca utuh, cek sumber dan tanggal, bandingkan, baru putuskan untuk membagikan.
                    """,
                ),
            ),
            keyTerms = listOf(
                term("Fakta", "Informasi yang dapat dibuktikan kebenarannya."),
                term("Opini", "Pendapat, perasaan, atau penilaian seseorang."),
                term("Hoaks", "Informasi bohong yang disebarkan seolah-olah benar."),
                term("Disinformasi", "Informasi keliru yang sengaja dibuat atau disebarkan untuk menipu."),
                term("Judul umpan klik (clickbait)", "Judul yang sengaja dibuat sensasional agar orang mengekliknya."),
            ),
            examples = listOf(
                "Pesan berantai di grup keluarga tentang obat ajaib tanpa sumber yang jelas adalah contoh informasi yang perlu dicek dulu.",
                "\"Harga beras di pasar itu naik minggu ini\" adalah fakta yang bisa dicek, sedangkan \"Pengelolaan pasar itu buruk\" adalah opini.",
            ),
            prompts = listOf(
                prompt(
                    "Bagaimana menegur dengan sopan anggota keluarga yang membagikan hoaks di grup?",
                    "Dengan nada santai dan tanpa menyalahkan, misalnya, \"Sepertinya info ini perlu dicek dulu. Saya cari di berita resmi belum ada. Ini tautan cek faktanya.\"",
                ),
                prompt(
                    "Coba bedakan fakta dan opini dalam kalimat: \"Timnas menang 2–1 tadi malam, permainannya luar biasa.\"",
                    "\"Timnas menang 2–1 tadi malam\" adalah fakta karena bisa dicek. \"Permainannya luar biasa\" adalah opini karena merupakan penilaian.",
                ),
            ),
            quiz = listOf(
                question(
                    "Manakah yang merupakan fakta?",
                    listOf(
                        "Film itu adalah film terbaik tahun ini",
                        "Pasar itu buka setiap hari pukul 06.00",
                        "Sebaiknya semua orang bangun pagi",
                        "Makanan itu terlalu pedas",
                    ),
                    1,
                    "Jam buka pasar bisa dicek kebenarannya. Pilihan lain berisi penilaian atau saran, sehingga termasuk opini.",
                ),
                question(
                    "Informasi keliru yang sengaja disebarkan untuk menipu disebut…",
                    listOf("Disinformasi", "Misinformasi", "Opini", "Fakta"),
                    0,
                    "Disinformasi disengaja. Misinformasi adalah informasi keliru yang disebarkan tanpa niat menipu.",
                ),
                question(
                    "Langkah pertama yang baik sebelum membagikan sebuah berita adalah…",
                    listOf(
                        "Langsung membagikannya agar orang lain tahu",
                        "Membaca judulnya saja",
                        "Membaca isinya sampai habis dan mengecek sumbernya",
                        "Menambahkan komentar sendiri",
                    ),
                    2,
                    "Membaca utuh dan mengecek sumber membantu kita menghindari ikut menyebarkan hoaks.",
                ),
                question(
                    "Dalam rumus 5W+1H, unsur When menanyakan…",
                    listOf("Siapa", "Di mana", "Mengapa", "Kapan"),
                    3,
                    "When berarti kapan. Who = siapa, Where = di mana, Why = mengapa.",
                ),
            ),
        ),
    ),
)
