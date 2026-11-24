package id.co.ptn.wawasanharian.data.seed

internal val stageBudaya = SeedStage(
    id = "s5",
    title = "Budaya dan Masyarakat",
    description = "Mengenal keberagaman Indonesia serta makna negara, bangsa, dan wilayah dalam kehidupan bersama.",
    lessons = listOf(
        SeedLesson(
            id = "s5-l1",
            title = "Mengenal budaya dan keberagaman Indonesia",
            summary = "Mengenal keberagaman suku, bahasa, dan budaya Indonesia, serta hal-hal yang mempersatukannya.",
            widgetTeaser = "Bhinneka Tunggal Ika berarti \"berbeda-beda tetapi tetap satu\", diambil dari Kitab Sutasoma karya Mpu Tantular.",
            sections = listOf(
                kenali(
                    "Apa itu budaya?",
                    """
                    Budaya adalah cara hidup sekelompok masyarakat yang diwariskan dari generasi ke generasi. Budaya meliputi bahasa, adat istiadat, kesenian, makanan, pakaian, hingga cara bergaul.

                    Indonesia sangat beragam. Menurut Sensus Penduduk 2010, terdapat lebih dari 1.300 kelompok suku bangsa di Indonesia. Ada pula ratusan bahasa daerah yang masih dituturkan.
                    """,
                ),
                ketahui(
                    "Pemersatu dalam keberagaman",
                    """
                    Di tengah keberagaman itu, ada beberapa hal yang mempersatukan kita:

                    • Bahasa Indonesia, yang berakar dari bahasa Melayu, menjadi bahasa persatuan. Hal ini diikrarkan dalam Sumpah Pemuda pada 28 Oktober 1928.
                    • Semboyan Bhinneka Tunggal Ika, yang berarti "berbeda-beda tetapi tetap satu".
                    • Pancasila sebagai dasar negara.
                    """,
                ),
                ketahui(
                    "Warisan budaya yang mendunia",
                    """
                    Sejumlah budaya Indonesia diakui UNESCO sebagai Warisan Budaya Takbenda, antara lain wayang, keris, batik, angklung, dan tari saman.

                    Batik diakui UNESCO pada 2 Oktober 2009. Sejak itu, tanggal 2 Oktober diperingati sebagai Hari Batik Nasional.
                    """,
                ),
                hubungkan(
                    "Hidup berdampingan",
                    """
                    Keberagaman paling terasa dalam kehidupan sehari-hari. Di kantor, sekolah, atau lingkungan rumah, kita bertemu orang dengan latar belakang suku, agama, dan kebiasaan yang berbeda.

                    Beberapa sikap sederhana membantu kita bergaul: bertanya dengan rasa ingin tahu, bukan menghakimi; menghormati hari besar dan kebiasaan orang lain; serta tidak menjadikan suku atau agama sebagai bahan ejekan. Nilai gotong royong—bekerja sama untuk kepentingan bersama—juga menjadi perekat masyarakat kita.
                    """,
                ),
                rangkum(
                    """
                    • Budaya adalah cara hidup yang diwariskan, dan Indonesia memiliki sangat banyak suku dan bahasa daerah.
                    • Pemersatu kita: bahasa Indonesia (Sumpah Pemuda 1928), Bhinneka Tunggal Ika, dan Pancasila.
                    • Wayang, keris, batik, angklung, dan saman diakui UNESCO; 2 Oktober adalah Hari Batik Nasional.
                    • Rasa ingin tahu, saling menghormati, dan gotong royong membantu kita hidup berdampingan.
                    """,
                ),
            ),
            keyTerms = listOf(
                term("Budaya", "Cara hidup sekelompok masyarakat yang diwariskan dari generasi ke generasi."),
                term("Suku bangsa", "Kelompok masyarakat yang memiliki kesamaan asal-usul, bahasa, dan adat istiadat."),
                term("Gotong royong", "Bekerja sama secara sukarela untuk kepentingan bersama."),
                term("Warisan budaya takbenda", "Tradisi, kesenian, atau keterampilan yang diwariskan dan diakui nilainya, seperti batik dan wayang."),
            ),
            examples = listOf(
                "Banyak kantor dan sekolah mengenakan batik pada hari-hari tertentu, termasuk pada Hari Batik Nasional, 2 Oktober.",
                "Saat kerja bakti membersihkan lingkungan, warga dari berbagai latar belakang bekerja bersama—contoh nyata gotong royong.",
            ),
            prompts = listOf(
                prompt(
                    "Rekan kerja barumu berasal dari daerah lain. Bagaimana memulai obrolan tentang budayanya dengan sopan?",
                    "Coba bertanya dengan rasa ingin tahu, misalnya, \"Di daerahmu, makanan khas apa yang wajib dicoba?\" atau \"Ada tradisi unik saat hari raya di sana?\"",
                ),
                prompt(
                    "Menurutmu, apa arti Bhinneka Tunggal Ika dalam kehidupan sehari-hari?",
                    "Artinya kita boleh berbeda suku, bahasa, atau agama, tetapi tetap satu sebagai bangsa Indonesia dan saling menghormati.",
                ),
            ),
            quiz = listOf(
                question(
                    "Semboyan Bhinneka Tunggal Ika berarti…",
                    listOf("Bersatu kita teguh", "Berbeda-beda tetapi tetap satu", "Bekerja sama untuk bersama", "Satu nusa satu bangsa"),
                    1,
                    "Bhinneka Tunggal Ika berarti \"berbeda-beda tetapi tetap satu\". Semboyan ini berasal dari Kitab Sutasoma.",
                ),
                question(
                    "Sumpah Pemuda, yang menegaskan bahasa Indonesia sebagai bahasa persatuan, diikrarkan pada tahun…",
                    listOf("1908", "1945", "1928", "1966"),
                    2,
                    "Sumpah Pemuda diikrarkan pada 28 Oktober 1928, sebelum Indonesia merdeka pada 1945.",
                ),
                question(
                    "Mengapa 2 Oktober diperingati sebagai Hari Batik Nasional?",
                    listOf(
                        "Batik diakui UNESCO pada tanggal itu tahun 2009",
                        "Itu hari lahir seorang pembatik terkenal",
                        "Batik pertama kali dibuat pada tanggal itu",
                        "Itu bertepatan dengan Hari Sumpah Pemuda",
                    ),
                    0,
                    "Batik diakui UNESCO sebagai Warisan Budaya Takbenda pada 2 Oktober 2009, sehingga tanggal itu menjadi Hari Batik Nasional.",
                ),
                question(
                    "Bahasa Indonesia berakar dari bahasa…",
                    listOf("Jawa", "Sanskerta", "Belanda", "Melayu"),
                    3,
                    "Bahasa Indonesia berakar dari bahasa Melayu, yang sejak lama dipakai sebagai bahasa pergaulan dan perdagangan di Nusantara.",
                ),
            ),
        ),
        SeedLesson(
            id = "s5-l2",
            title = "Apa perbedaan negara, bangsa, dan wilayah?",
            summary = "Memahami makna bangsa, unsur-unsur negara, dan bagaimana wilayah Indonesia—darat, laut, dan udara—ditetapkan.",
            widgetTeaser = "Bangsa adalah kumpulan orang yang merasa satu; negara adalah organisasi yang memiliki rakyat, wilayah, dan pemerintahan.",
            sections = listOf(
                kenali(
                    "Bangsa dan negara itu berbeda",
                    """
                    Dua kata ini sering dipakai bergantian, padahal artinya berbeda.

                    Bangsa adalah sekelompok manusia yang merasa satu karena kesamaan sejarah, nasib, cita-cita, dan keinginan untuk hidup bersama.

                    Negara adalah organisasi kekuasaan yang memiliki rakyat, wilayah, dan pemerintahan yang berdaulat. Rasa persatuan bangsa Indonesia sudah tumbuh—misalnya lewat Sumpah Pemuda 1928—sebelum Negara Indonesia diproklamasikan pada 17 Agustus 1945.
                    """,
                ),
                ketahui(
                    "Unsur-unsur negara",
                    """
                    Suatu negara memiliki unsur-unsur berikut:

                    • Rakyat: orang-orang yang tinggal di negara itu dan tunduk pada aturannya.
                    • Wilayah: daerah tempat negara itu berkuasa.
                    • Pemerintahan yang berdaulat: pemerintah yang menjalankan kekuasaan dan tidak tunduk pada negara lain.

                    Ketiganya disebut unsur utama (konstitutif). Pengakuan dari negara lain sering disebut unsur tambahan (deklaratif), yang memudahkan negara menjalin hubungan internasional.
                    """,
                ),
                ketahui(
                    "Wilayah: darat, laut, dan udara",
                    """
                    Wilayah negara tidak hanya berupa daratan, tetapi juga laut dan ruang udara di atasnya.

                    Dulu, sebagian laut di antara pulau-pulau Indonesia dianggap sebagai laut bebas. Melalui Deklarasi Djuanda tahun 1957, Indonesia menyatakan bahwa laut di sekitar dan di antara pulau-pulaunya adalah satu kesatuan dengan wilayah daratan. Prinsip negara kepulauan ini kemudian diakui dalam Konvensi Hukum Laut PBB (UNCLOS) tahun 1982.

                    Berdasarkan UNCLOS, laut teritorial lebarnya hingga 12 mil laut, sedangkan Zona Ekonomi Eksklusif (ZEE) hingga 200 mil laut, diukur dari garis pangkal.
                    """,
                ),
                hubungkan(
                    "Dalam kehidupan sehari-hari",
                    """
                    Saat ada kapal asing tertangkap mencuri ikan, berita sering menyebut ZEE—wilayah laut tempat Indonesia berhak memanfaatkan kekayaan alamnya, seperti ikan.

                    KTP dan paspor adalah bukti bahwa kita bagian dari rakyat sebuah negara. Sementara itu, rasa bangga saat tim Indonesia bertanding di ajang internasional adalah contoh rasa kebangsaan.
                    """,
                ),
                rangkum(
                    """
                    • Bangsa adalah kumpulan orang yang merasa satu; negara adalah organisasi kekuasaan.
                    • Unsur utama negara: rakyat, wilayah, dan pemerintahan yang berdaulat; pengakuan adalah unsur tambahan.
                    • Wilayah negara meliputi darat, laut, dan udara.
                    • Deklarasi Djuanda 1957 menyatukan laut dan pulau Indonesia; ZEE membentang hingga 200 mil laut.
                    """,
                ),
            ),
            keyTerms = listOf(
                term("Bangsa", "Kelompok manusia yang merasa satu karena kesamaan sejarah, nasib, dan cita-cita."),
                term("Kedaulatan", "Kekuasaan tertinggi suatu negara untuk mengatur dirinya sendiri tanpa campur tangan negara lain."),
                term("Deklarasi Djuanda", "Pernyataan Indonesia tahun 1957 bahwa laut di antara pulau-pulaunya adalah bagian dari wilayah Indonesia."),
                term("Zona Ekonomi Eksklusif (ZEE)", "Wilayah laut hingga 200 mil laut dari garis pangkal, tempat negara berhak atas kekayaan alamnya."),
            ),
            examples = listOf(
                "Berita penangkapan kapal pencuri ikan sering menyebut ZEE Indonesia—wilayah laut yang kekayaan alamnya menjadi hak Indonesia.",
                "Saat bepergian ke luar negeri, paspor menunjukkan bahwa kita adalah warga negara Indonesia.",
            ),
            prompts = listOf(
                prompt(
                    "Bagaimana menjelaskan perbedaan bangsa dan negara kepada teman?",
                    "Bangsa itu soal rasa satu—karena sejarah dan cita-cita yang sama. Negara itu organisasinya: ada rakyat, wilayah, dan pemerintahan yang berdaulat.",
                ),
                prompt(
                    "Mengapa Deklarasi Djuanda penting bagi Indonesia?",
                    "Karena deklarasi itu menyatukan laut dan pulau-pulau Indonesia sebagai satu wilayah. Tanpa itu, laut di antara pulau-pulau kita bisa dianggap laut bebas.",
                ),
            ),
            quiz = listOf(
                question(
                    "Manakah yang termasuk unsur utama (konstitutif) negara?",
                    listOf(
                        "Lagu kebangsaan dan bendera",
                        "Rakyat, wilayah, dan pemerintahan yang berdaulat",
                        "Pengakuan dari negara lain",
                        "Mata uang sendiri",
                    ),
                    1,
                    "Unsur utama negara adalah rakyat, wilayah, dan pemerintahan yang berdaulat. Pengakuan dari negara lain termasuk unsur tambahan.",
                ),
                question(
                    "Deklarasi Djuanda tahun 1957 menegaskan bahwa…",
                    listOf(
                        "Laut di antara pulau-pulau Indonesia adalah satu kesatuan dengan wilayah daratan",
                        "Indonesia bergabung dengan PBB",
                        "Bahasa Indonesia menjadi bahasa persatuan",
                        "Indonesia dibagi menjadi tiga zona waktu",
                    ),
                    0,
                    "Deklarasi Djuanda menyatakan laut di sekitar dan di antara pulau-pulau Indonesia sebagai bagian dari wilayah Indonesia.",
                ),
                question(
                    "Zona Ekonomi Eksklusif (ZEE) membentang hingga berapa jauh dari garis pangkal?",
                    listOf("12 mil laut", "24 mil laut", "200 mil laut", "500 mil laut"),
                    2,
                    "ZEE membentang hingga 200 mil laut. Angka 12 mil laut adalah batas laut teritorial.",
                ),
                question(
                    "Kumpulan orang yang merasa satu karena kesamaan sejarah dan cita-cita disebut…",
                    listOf("Negara", "Pemerintahan", "Wilayah", "Bangsa"),
                    3,
                    "Itulah pengertian bangsa. Negara adalah organisasi kekuasaan yang memiliki rakyat, wilayah, dan pemerintahan.",
                ),
            ),
        ),
    ),
)
