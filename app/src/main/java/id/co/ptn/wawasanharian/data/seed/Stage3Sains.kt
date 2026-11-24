package id.co.ptn.wawasanharian.data.seed

internal val stageSains = SeedStage(
    id = "s3",
    title = "Sains dan Kehidupan Sehari-hari",
    description = "Memahami fenomena alam dan tubuh manusia yang kita temui setiap hari, dijelaskan dengan sederhana.",
    lessons = listOf(
        SeedLesson(
            id = "s3-l1",
            title = "Mengapa terjadi pergantian siang dan malam?",
            summary = "Memahami rotasi dan revolusi Bumi, penyebab siang dan malam, tahun kabisat, dan musim.",
            widgetTeaser = "Matahari tampak terbit di timur karena Bumi berputar dari barat ke timur.",
            sections = listOf(
                kenali(
                    "Bumi berputar",
                    """
                    Bumi terus berputar pada porosnya. Gerakan ini disebut rotasi, dan satu putaran penuh memakan waktu sekitar 24 jam—itulah satu hari.

                    Matahari hanya bisa menyinari separuh Bumi pada satu waktu. Bagian yang menghadap Matahari mengalami siang, sedangkan bagian yang membelakanginya mengalami malam. Karena Bumi terus berputar, siang dan malam pun bergantian.
                    """,
                ),
                ketahui(
                    "Mengapa Matahari terbit di timur?",
                    """
                    Bumi berotasi dari barat ke timur. Akibatnya, dari tempat kita berdiri, Matahari tampak bergerak ke arah sebaliknya: terbit di timur dan terbenam di barat. Sebenarnya bukan Matahari yang mengelilingi kita setiap hari—kitalah yang ikut berputar bersama Bumi.

                    Rotasi ini juga membuat wilayah timur Indonesia melihat Matahari terbit lebih dulu. Itulah salah satu alasan Papua memakai WIT, dua jam lebih cepat dari WIB.
                    """,
                ),
                ketahui(
                    "Revolusi dan tahun kabisat",
                    """
                    Selain berputar pada porosnya, Bumi juga bergerak mengelilingi Matahari. Gerakan ini disebut revolusi, dan satu putaran penuh memakan waktu sekitar 365¼ hari—itulah satu tahun.

                    Kelebihan seperempat hari itu dikumpulkan sehingga, umumnya setiap empat tahun sekali, ada satu hari tambahan: 29 Februari. Tahun yang memiliki tanggal itu disebut tahun kabisat.
                    """,
                ),
                hubungkan(
                    "Musim di Indonesia dan di negara lain",
                    """
                    Poros Bumi miring sekitar 23,5 derajat. Kemiringan inilah—bukan jarak Bumi ke Matahari—yang menyebabkan negara-negara yang jauh dari khatulistiwa mengalami empat musim.

                    Indonesia berada di sekitar khatulistiwa, sehingga panjang siang dan malam hampir sama sepanjang tahun, masing-masing sekitar 12 jam. Kita tidak mengalami musim dingin bersalju; yang kita alami adalah musim kemarau dan musim hujan, yang terutama dipengaruhi angin muson.
                    """,
                ),
                rangkum(
                    """
                    • Rotasi Bumi (sekitar 24 jam) menyebabkan siang dan malam.
                    • Bumi berotasi dari barat ke timur, sehingga Matahari tampak terbit di timur.
                    • Revolusi Bumi (sekitar 365¼ hari) menjadi dasar satu tahun dan tahun kabisat.
                    • Empat musim disebabkan kemiringan poros Bumi; Indonesia mengenal musim kemarau dan hujan.
                    """,
                ),
            ),
            keyTerms = listOf(
                term("Rotasi", "Perputaran Bumi pada porosnya, sekitar 24 jam sekali putaran."),
                term("Revolusi", "Pergerakan Bumi mengelilingi Matahari, sekitar 365¼ hari sekali putaran."),
                term("Tahun kabisat", "Tahun yang memiliki 366 hari karena ada tanggal 29 Februari."),
                term("Angin muson", "Angin yang arahnya berganti kira-kira setiap setengah tahun dan membawa musim hujan atau kemarau."),
            ),
            examples = listOf(
                "Saat menelepon teman di Eropa, sering kali di sana masih pagi, sementara di Indonesia sudah sore—akibat rotasi Bumi dan perbedaan zona waktu.",
                "Orang yang lahir pada 29 Februari hanya bisa merayakan tanggal ulang tahunnya yang \"asli\" pada tahun kabisat.",
            ),
            prompts = listOf(
                prompt(
                    "Anak kecil bertanya, \"Kenapa malam itu gelap?\" Bagaimana menjawabnya dengan sederhana?",
                    "Karena Bumi itu bulat dan terus berputar. Saat bagian Bumi tempat kita tinggal membelakangi Matahari, cahayanya tidak sampai ke kita, jadi gelap.",
                ),
                prompt(
                    "Kenapa Indonesia tidak punya musim salju?",
                    "Karena kita dekat khatulistiwa, jadi sinar Matahari yang kita terima hampir sama sepanjang tahun. Musim kita ditentukan oleh hujan dan kemarau.",
                ),
            ),
            quiz = listOf(
                question(
                    "Apa penyebab terjadinya siang dan malam?",
                    listOf("Revolusi Bumi", "Rotasi Bumi", "Gerhana Matahari", "Pergerakan Bulan"),
                    1,
                    "Rotasi Bumi membuat bagian Bumi bergantian menghadap dan membelakangi Matahari, sehingga terjadi siang dan malam.",
                ),
                question(
                    "Matahari tampak terbit di timur karena…",
                    listOf(
                        "Matahari mengelilingi Bumi setiap hari",
                        "Bumi berotasi dari timur ke barat",
                        "Bumi berotasi dari barat ke timur",
                        "Bulan mendorong Matahari ke timur",
                    ),
                    2,
                    "Karena Bumi berputar dari barat ke timur, Matahari tampak bergerak ke arah sebaliknya: muncul di timur dan tenggelam di barat.",
                ),
                question(
                    "Mengapa ada tahun kabisat?",
                    listOf(
                        "Karena revolusi Bumi sekitar 365¼ hari",
                        "Karena putaran Bumi makin lambat",
                        "Karena kalender salah cetak",
                        "Karena rotasi Bumi 25 jam",
                    ),
                    0,
                    "Kelebihan sekitar seperempat hari setiap tahun dikumpulkan menjadi satu hari tambahan, umumnya setiap empat tahun.",
                ),
                question(
                    "Apa penyebab utama empat musim di negara-negara yang jauh dari khatulistiwa?",
                    listOf("Jarak Bumi ke Matahari berubah", "Kemiringan poros Bumi", "Angin muson", "Rotasi Bulan"),
                    1,
                    "Kemiringan poros Bumi sekitar 23,5 derajat membuat sudut dan lama penyinaran Matahari berubah sepanjang tahun. Anggapan bahwa musim disebabkan jarak ke Matahari adalah salah kaprah yang umum.",
                ),
            ),
        ),
        SeedLesson(
            id = "s3-l2",
            title = "Mengenal organ tubuh manusia",
            summary = "Mengenal organ-organ penting—otak, jantung, paru-paru, organ pencernaan, hati, dan ginjal—serta fungsinya.",
            widgetTeaser = "Kulit adalah organ terbesar di tubuh manusia. Ia melindungi kita dari kuman dan membantu mengatur suhu tubuh.",
            sections = listOf(
                kenali(
                    "Tubuh sebagai satu tim",
                    """
                    Organ adalah bagian tubuh yang memiliki tugas khusus, misalnya jantung atau paru-paru. Organ-organ ini bekerja sama dalam kelompok yang disebut sistem organ, seperti sistem pernapasan dan sistem pencernaan.

                    Memahami cara kerja organ membantu kita mengikuti obrolan sehari-hari tentang kesehatan—dan menjaga tubuh dengan lebih baik.
                    """,
                ),
                ketahui(
                    "Otak, jantung, dan paru-paru",
                    """
                    • Otak adalah pusat kendali tubuh. Otak mengatur gerakan, pikiran, ingatan, dan perasaan, serta mengolah informasi dari pancaindra.
                    • Jantung adalah otot yang memompa darah ke seluruh tubuh. Darah membawa oksigen dan zat makanan ke setiap sel.
                    • Paru-paru adalah tempat pertukaran gas: oksigen dari udara masuk ke darah, dan karbon dioksida dikeluarkan saat kita mengembuskan napas.
                    """,
                ),
                ketahui(
                    "Pencernaan, hati, dan ginjal",
                    """
                    • Lambung mencerna makanan dengan bantuan asam lambung.
                    • Usus halus menyerap sari-sari makanan ke dalam darah. Usus besar menyerap air dari sisa makanan.
                    • Hati (dalam istilah kedokteran: liver) menetralkan zat beracun, menyimpan cadangan energi, dan menghasilkan empedu untuk membantu mencerna lemak.
                    • Ginjal menyaring darah serta membuang zat sisa dan kelebihan air dalam bentuk urine.

                    Perhatikan: dalam percakapan sehari-hari, kata "hati" sering dipakai untuk perasaan. Namun, secara medis hati adalah organ di perut kanan atas.
                    """,
                ),
                hubungkan(
                    "Kebiasaan sehari-hari dan organ kita",
                    """
                    Banyak nasihat kesehatan berkaitan langsung dengan organ tubuh:

                    • Minum air yang cukup membantu kerja ginjal.
                    • Tidak merokok melindungi paru-paru dan jantung.
                    • Aktivitas fisik yang rutin baik untuk jantung.
                    • Tidur yang cukup penting bagi otak.

                    Informasi ini bersifat umum. Untuk keluhan kesehatan, tetap periksakan diri ke tenaga kesehatan.
                    """,
                ),
                rangkum(
                    """
                    • Organ bekerja sama dalam sistem organ.
                    • Otak mengendalikan tubuh; jantung memompa darah; paru-paru menukar oksigen dan karbon dioksida.
                    • Usus halus menyerap sari makanan; hati (liver) menetralkan racun; ginjal menyaring darah menjadi urine.
                    • Kulit adalah organ terbesar di tubuh manusia.
                    """,
                ),
            ),
            keyTerms = listOf(
                term("Organ", "Bagian tubuh yang memiliki tugas khusus, seperti jantung atau ginjal."),
                term("Oksigen", "Gas di udara yang dibutuhkan sel-sel tubuh untuk menghasilkan energi."),
                term("Empedu", "Cairan yang dihasilkan hati untuk membantu mencerna lemak."),
                term("Urine", "Air seni; cairan berisi zat sisa yang disaring oleh ginjal."),
            ),
            examples = listOf(
                "Saat naik tangga dengan cepat, napas dan detak jantung bertambah cepat karena otot membutuhkan lebih banyak oksigen.",
                "Ketika dokter menyarankan cek fungsi ginjal, artinya dokter ingin tahu seberapa baik ginjal menyaring darah.",
            ),
            prompts = listOf(
                prompt(
                    "Teman bilang \"hati\" itu organ untuk perasaan. Bagaimana kamu meluruskannya dengan sopan?",
                    "Dalam bahasa sehari-hari memang begitu. Tapi secara medis, hati itu liver—organ di perut kanan atas yang menetralkan racun dan menghasilkan empedu.",
                ),
                prompt(
                    "Kenapa olahraga membuat napas terengah-engah?",
                    "Otot yang bekerja butuh lebih banyak oksigen. Paru-paru bernapas lebih cepat untuk mengambil oksigen, dan jantung memompa darah lebih cepat untuk mengantarkannya.",
                ),
            ),
            quiz = listOf(
                question(
                    "Organ yang menyaring darah dan menghasilkan urine adalah…",
                    listOf("Hati", "Lambung", "Ginjal", "Paru-paru"),
                    2,
                    "Ginjal menyaring darah, lalu membuang zat sisa dan kelebihan air dalam bentuk urine.",
                ),
                question(
                    "Di mana sebagian besar sari makanan diserap ke dalam darah?",
                    listOf("Usus halus", "Lambung", "Usus besar", "Kerongkongan"),
                    0,
                    "Usus halus menyerap sari-sari makanan. Usus besar terutama menyerap air dari sisa makanan.",
                ),
                question(
                    "Dalam istilah kedokteran, \"hati\" merujuk pada organ…",
                    listOf("Jantung", "Liver", "Limpa", "Pankreas"),
                    1,
                    "Hati adalah liver, organ yang menetralkan racun, menyimpan cadangan energi, dan menghasilkan empedu.",
                ),
                question(
                    "Organ terbesar di tubuh manusia adalah…",
                    listOf("Otak", "Hati", "Paru-paru", "Kulit"),
                    3,
                    "Kulit adalah organ terbesar di tubuh manusia. Di antara organ di dalam tubuh, hati (liver) adalah yang terbesar.",
                ),
            ),
        ),
    ),
)
