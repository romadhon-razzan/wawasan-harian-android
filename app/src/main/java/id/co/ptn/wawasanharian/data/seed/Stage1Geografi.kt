package id.co.ptn.wawasanharian.data.seed

internal val stageGeografi = SeedStage(
    id = "s1",
    title = "Geografi dan Dunia",
    description = "Mengenal bentuk dunia—benua, samudra, dan posisi Indonesia di peta—agar nyaman ikut obrolan tentang negara dan wilayah.",
    lessons = listOf(
        SeedLesson(
            id = "s1-l1",
            title = "Apa itu Bumi, benua, dan samudra?",
            summary = "Mengenal Bumi sebagai planet tempat kita tinggal, tujuh benua, lima samudra, dan letak Indonesia di antaranya.",
            widgetTeaser = "Sekitar 71% permukaan Bumi tertutup air. Indonesia berada di antara dua benua dan dua samudra.",
            sections = listOf(
                kenali(
                    "Bumi, rumah kita",
                    """
                    Bumi adalah planet ketiga dari Matahari dan satu-satunya planet yang sejauh ini diketahui memiliki kehidupan.

                    Jika dilihat dari luar angkasa, Bumi tampak biru karena sebagian besar permukaannya—sekitar 71%—tertutup air. Sisanya berupa daratan tempat manusia tinggal.
                    """,
                ),
                ketahui(
                    "Tujuh benua",
                    """
                    Benua adalah daratan yang sangat luas. Cara yang paling umum membagi daratan Bumi menjadi tujuh benua: Asia, Afrika, Amerika Utara, Amerika Selatan, Antarktika, Eropa, dan Australia.

                    Asia adalah benua terbesar, dan Indonesia termasuk di dalamnya. Australia adalah benua terkecil. Antarktika di kutub selatan hampir seluruhnya tertutup es dan tidak memiliki penduduk tetap.

                    Catatan kecil: sebagian buku menghitung jumlah benua secara berbeda, misalnya menggabungkan Amerika Utara dan Amerika Selatan. Jadi, kalau kamu mendengar angka lain, itu soal cara membagi, bukan berarti salah.
                    """,
                ),
                ketahui(
                    "Lima samudra",
                    """
                    Samudra adalah lautan yang sangat luas yang menghubungkan benua-benua. Lima samudra yang umum dikenal adalah Pasifik, Atlantik, Hindia, Arktik, dan Selatan.

                    Samudra Pasifik adalah yang terbesar sekaligus terdalam. Samudra Arktik di sekitar Kutub Utara adalah yang terkecil. Samudra Selatan mengelilingi Antarktika.

                    Bedakan samudra dengan laut: laut biasanya lebih kecil dan sebagian dikelilingi daratan, misalnya Laut Jawa.
                    """,
                ),
                hubungkan(
                    "Posisi silang Indonesia",
                    """
                    Indonesia terletak di antara dua benua, yaitu Asia dan Australia, serta di antara dua samudra, yaitu Samudra Hindia dan Samudra Pasifik. Letak ini sering disebut posisi silang.

                    Karena posisi itu, sejak dulu wilayah Nusantara menjadi jalur perdagangan dan tempat bertemunya berbagai budaya. Garis khatulistiwa juga melintasi Indonesia—salah satu kota yang dilewatinya adalah Pontianak di Kalimantan Barat.
                    """,
                ),
                rangkum(
                    """
                    • Bumi adalah planet ketiga dari Matahari; sekitar 71% permukaannya berupa air.
                    • Umumnya dikenal tujuh benua; Asia terbesar dan Australia terkecil.
                    • Ada lima samudra; Pasifik yang terbesar.
                    • Indonesia berada di posisi silang: antara Asia–Australia dan antara Samudra Hindia–Pasifik.
                    """,
                ),
            ),
            keyTerms = listOf(
                term("Benua", "Daratan yang sangat luas, seperti Asia atau Afrika."),
                term("Samudra", "Lautan sangat luas yang menghubungkan benua-benua."),
                term("Khatulistiwa", "Garis khayal yang membagi Bumi menjadi belahan utara dan belahan selatan."),
                term("Posisi silang", "Letak Indonesia di antara dua benua dan dua samudra."),
            ),
            examples = listOf(
                "Saat membuka peta di aplikasi navigasi, coba perkecil tampilannya sampai terlihat benua Asia dan Australia—Indonesia ada di antaranya.",
                "Tugu Khatulistiwa di Pontianak menjadi tempat wisata karena kota itu dilalui garis khatulistiwa.",
            ),
            prompts = listOf(
                prompt(
                    "Kalau ada yang bertanya, \"Kenapa letak Indonesia disebut strategis?\", bagaimana kamu menjawab?",
                    "Karena Indonesia ada di posisi silang—di antara benua Asia dan Australia, serta Samudra Hindia dan Pasifik. Jadi, sejak dulu jalur perdagangan dunia banyak melewati wilayah kita.",
                ),
                prompt(
                    "Teman bilang benua itu ada lima. Bagaimana menanggapinya dengan santai?",
                    "Bisa jadi benar, tergantung cara menghitungnya. Yang paling umum dipakai sekarang tujuh benua, tetapi ada juga yang menggabungkan beberapa benua.",
                ),
            ),
            quiz = listOf(
                question(
                    "Samudra apa yang terbesar di dunia?",
                    listOf("Samudra Atlantik", "Samudra Pasifik", "Samudra Hindia", "Samudra Arktik"),
                    1,
                    "Samudra Pasifik adalah samudra terbesar sekaligus terdalam di dunia.",
                ),
                question(
                    "Indonesia terletak di antara dua samudra, yaitu…",
                    listOf("Pasifik dan Atlantik", "Hindia dan Arktik", "Hindia dan Pasifik", "Atlantik dan Selatan"),
                    2,
                    "Indonesia diapit Samudra Hindia di barat daya dan Samudra Pasifik di timur laut. Inilah bagian dari posisi silang Indonesia.",
                ),
                question(
                    "Benua terbesar di dunia adalah…",
                    listOf("Afrika", "Asia", "Amerika Utara", "Eropa"),
                    1,
                    "Asia adalah benua terbesar, baik dari luas wilayah maupun jumlah penduduknya. Indonesia termasuk di benua Asia.",
                ),
                question(
                    "Kira-kira berapa bagian permukaan Bumi yang tertutup air?",
                    listOf("Sekitar 30%", "Sekitar 50%", "Sekitar 71%", "Sekitar 90%"),
                    2,
                    "Sekitar 71% permukaan Bumi tertutup air, sebagian besar berupa samudra. Itu sebabnya Bumi tampak biru dari luar angkasa.",
                ),
            ),
        ),
        SeedLesson(
            id = "s1-l2",
            title = "Mengenal Indonesia dan negara-negara tetangga",
            summary = "Mengenal Indonesia sebagai negara kepulauan, pulau-pulau besarnya, pembagian zona waktu, dan negara-negara tetangganya.",
            widgetTeaser = "Indonesia berbatasan darat dengan tiga negara: Malaysia, Papua Nugini, dan Timor Leste.",
            sections = listOf(
                kenali(
                    "Negara kepulauan",
                    """
                    Indonesia adalah negara kepulauan terbesar di dunia. Wilayahnya terdiri atas ribuan pulau—lebih dari enam belas ribu di antaranya sudah dibakukan namanya—yang dihubungkan oleh laut.

                    Lima pulau besarnya adalah Sumatra, Jawa, Kalimantan, Sulawesi, dan Papua. Selain itu, ada gugusan kepulauan seperti Nusa Tenggara dan Maluku.
                    """,
                ),
                ketahui(
                    "Tiga zona waktu",
                    """
                    Karena wilayahnya membentang jauh dari barat ke timur, Indonesia dibagi menjadi tiga zona waktu:

                    • WIB (Waktu Indonesia Barat), misalnya di Sumatra dan Jawa.
                    • WITA (Waktu Indonesia Tengah), satu jam lebih cepat dari WIB, misalnya di Bali dan Sulawesi.
                    • WIT (Waktu Indonesia Timur), dua jam lebih cepat dari WIB, misalnya di Maluku dan Papua.
                    """,
                ),
                ketahui(
                    "Tetangga di darat dan di laut",
                    """
                    Indonesia berbagi daratan dengan tiga negara:

                    • Malaysia, di Pulau Kalimantan. Pulau ini juga menjadi tempat Brunei Darussalam, meskipun Brunei tidak berbatasan darat langsung dengan Indonesia.
                    • Papua Nugini, di Pulau Papua.
                    • Timor Leste, di Pulau Timor.

                    Di laut, Indonesia bertetangga dengan negara-negara seperti Singapura, Filipina, Australia, Vietnam, Thailand, India, dan Palau.
                    """,
                ),
                hubungkan(
                    "Bertetangga di ASEAN",
                    """
                    Indonesia bersama Malaysia, Filipina, Singapura, dan Thailand mendirikan ASEAN (Perhimpunan Bangsa-Bangsa Asia Tenggara) pada 8 Agustus 1967 di Bangkok. Setelah itu, negara-negara Asia Tenggara lainnya ikut bergabung.

                    Itu sebabnya kita sering mendengar kerja sama ASEAN dalam berita, ajang olahraga seperti SEA Games, hingga kemudahan bepergian ke sesama negara anggota.
                    """,
                ),
                rangkum(
                    """
                    • Indonesia adalah negara kepulauan terbesar di dunia dengan lima pulau besar.
                    • Ada tiga zona waktu: WIB, WITA (+1 jam), dan WIT (+2 jam).
                    • Tetangga di darat: Malaysia, Papua Nugini, dan Timor Leste.
                    • Indonesia adalah salah satu dari lima pendiri ASEAN pada 1967.
                    """,
                ),
            ),
            keyTerms = listOf(
                term("Negara kepulauan", "Negara yang wilayahnya terdiri atas banyak pulau dan laut di antaranya sebagai satu kesatuan."),
                term("Zona waktu", "Wilayah yang memakai jam yang sama. Indonesia memiliki tiga zona waktu."),
                term("Perbatasan", "Garis yang memisahkan wilayah satu negara dengan negara lain, di darat maupun di laut."),
                term("ASEAN", "Perhimpunan negara-negara Asia Tenggara untuk bekerja sama di bidang ekonomi, sosial, dan keamanan."),
            ),
            examples = listOf(
                "Saat menelepon keluarga di Jayapura pukul 19.00 WIB, di sana sudah pukul 21.00 WIT.",
                "Warga di perbatasan Kalimantan Barat dapat melintas ke Malaysia melalui pos lintas batas resmi, seperti di Entikong.",
            ),
            prompts = listOf(
                prompt(
                    "Teman dari luar negeri bertanya, \"Indonesia dekat dengan negara apa saja?\" Coba jawab singkat.",
                    "Di darat kami berbatasan dengan Malaysia, Papua Nugini, dan Timor Leste. Di laut kami bertetangga dengan Singapura, Filipina, Australia, dan beberapa negara lain.",
                ),
                prompt(
                    "Bagaimana menjelaskan kenapa jam di Bali berbeda dengan di Jakarta?",
                    "Karena Indonesia sangat luas dari barat ke timur, jadi dibagi tiga zona waktu. Bali memakai WITA, satu jam lebih cepat dari Jakarta yang memakai WIB.",
                ),
            ),
            quiz = listOf(
                question(
                    "Negara mana yang berbagi Pulau Timor dengan Indonesia?",
                    listOf("Australia", "Timor Leste", "Papua Nugini", "Filipina"),
                    1,
                    "Pulau Timor terbagi antara Indonesia (bagian barat, Nusa Tenggara Timur) dan Timor Leste.",
                ),
                question(
                    "Indonesia memiliki berapa zona waktu?",
                    listOf("Tiga", "Dua", "Empat", "Lima"),
                    0,
                    "Indonesia memiliki tiga zona waktu: WIB, WITA, dan WIT.",
                ),
                question(
                    "Jika di Jakarta pukul 08.00 WIB, pukul berapa di Jayapura yang memakai WIT?",
                    listOf("07.00", "09.00", "10.00", "08.00"),
                    2,
                    "WIT dua jam lebih cepat dari WIB, jadi pukul 08.00 WIB sama dengan pukul 10.00 WIT.",
                ),
                question(
                    "ASEAN didirikan pada 1967 oleh lima negara. Manakah yang termasuk pendirinya?",
                    listOf("Vietnam", "Myanmar", "Kamboja", "Indonesia"),
                    3,
                    "Pendiri ASEAN adalah Indonesia, Malaysia, Filipina, Singapura, dan Thailand. Vietnam, Myanmar, dan Kamboja bergabung kemudian.",
                ),
            ),
        ),
    ),
)
