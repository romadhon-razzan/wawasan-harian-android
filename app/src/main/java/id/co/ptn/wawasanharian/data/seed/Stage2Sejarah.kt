package id.co.ptn.wawasanharian.data.seed

internal val stageSejarah = SeedStage(
    id = "s2",
    title = "Sejarah dan Peradaban",
    description = "Mengenal bagaimana peradaban manusia dimulai dan kerajaan-kerajaan besar yang pernah ada di Nusantara.",
    lessons = listOf(
        SeedLesson(
            id = "s2-l1",
            title = "Bagaimana manusia mulai mengenal tulisan dan peradaban?",
            summary = "Mengenal peralihan dari masa praaksara ke masa sejarah, peradaban sungai pertama, dan awal tulisan di Nusantara.",
            widgetTeaser = "Tulisan menjadi pembatas antara masa praaksara dan masa sejarah. Di Indonesia, bukti tertulis tertua berasal dari Kerajaan Kutai.",
            sections = listOf(
                kenali(
                    "Dari berburu ke bercocok tanam",
                    """
                    Dahulu manusia hidup berpindah-pindah, berburu hewan dan mengumpulkan makanan. Kemudian manusia mulai bercocok tanam dan beternak. Perubahan besar ini membuat mereka bisa menetap di satu tempat.

                    Dari permukiman yang menetap lahirlah desa, lalu kota. Kehidupan menjadi lebih rumit: ada perdagangan, pembagian kerja, dan aturan bersama. Kehidupan masyarakat yang sudah teratur seperti ini disebut peradaban.
                    """,
                ),
                ketahui(
                    "Lahirnya tulisan",
                    """
                    Seiring berkembangnya perdagangan dan pemerintahan, manusia perlu mencatat—misalnya jumlah hasil panen atau pajak. Dari kebutuhan itulah lahir tulisan.

                    Salah satu sistem tulisan tertua berasal dari bangsa Sumeria di Mesopotamia, sekitar 5.000 tahun lalu. Bentuk hurufnya seperti paku, sehingga disebut aksara paku. Di Mesir Kuno berkembang hieroglif, yaitu tulisan berupa gambar.

                    Masa sebelum manusia mengenal tulisan disebut masa praaksara (prasejarah). Masa setelah manusia mengenal tulisan disebut masa sejarah.
                    """,
                ),
                ketahui(
                    "Peradaban di tepi sungai",
                    """
                    Banyak peradaban awal tumbuh di dekat sungai besar, karena sungai menyediakan air, tanah yang subur, dan jalur transportasi. Contohnya:

                    • Mesopotamia, di antara Sungai Tigris dan Eufrat (kini sebagian besar wilayah Irak).
                    • Mesir Kuno, di sepanjang Sungai Nil.
                    • Lembah Sungai Indus, di wilayah Pakistan dan India barat laut sekarang.
                    • Tiongkok Kuno, di sekitar Sungai Kuning (Huang He).
                    """,
                ),
                hubungkan(
                    "Awal tulisan di Nusantara",
                    """
                    Di Indonesia, masa sejarah dimulai dengan prasasti Yupa dari Kerajaan Kutai di Kalimantan Timur. Prasasti berupa tiang batu ini ditulis dengan huruf Pallawa dan berbahasa Sanskerta, yang berasal dari India.

                    Dari pengaruh tulisan India, berkembang pula aksara-aksara Nusantara, seperti aksara Jawa (hanacaraka), aksara Bali, dan aksara Bugis (lontara). Sampai sekarang aksara-aksara ini masih bisa kamu lihat, misalnya pada papan nama jalan di Yogyakarta atau Bali.
                    """,
                ),
                rangkum(
                    """
                    • Bercocok tanam membuat manusia menetap, lalu membentuk desa, kota, dan peradaban.
                    • Tulisan lahir dari kebutuhan mencatat; aksara paku Sumeria termasuk yang tertua.
                    • Tulisan memisahkan masa praaksara dan masa sejarah.
                    • Di Indonesia, masa sejarah dimulai dengan prasasti Yupa dari Kutai.
                    """,
                ),
            ),
            keyTerms = listOf(
                term("Peradaban", "Kehidupan masyarakat yang sudah teratur, dengan kota, aturan, pembagian kerja, dan budaya."),
                term("Masa praaksara", "Masa ketika manusia belum mengenal tulisan; disebut juga prasejarah."),
                term("Prasasti", "Tulisan kuno yang dipahat pada batu, logam, atau benda keras lainnya."),
                term("Aksara", "Sistem tanda atau huruf untuk menuliskan bahasa."),
            ),
            examples = listOf(
                "Papan nama jalan di Yogyakarta sering ditulis dua kali: dengan huruf Latin dan dengan aksara Jawa.",
                "Mencatat pengeluaran di buku atau aplikasi sebenarnya meneruskan kebiasaan bangsa Sumeria: mencatat agar tidak lupa dan bisa dipertanggungjawabkan.",
            ),
            prompts = listOf(
                prompt(
                    "Bagaimana menjelaskan perbedaan masa praaksara dan masa sejarah dalam satu kalimat?",
                    "Masa praaksara adalah masa sebelum manusia mengenal tulisan, sedangkan masa sejarah dimulai sejak ada catatan tertulis.",
                ),
                prompt(
                    "Kenapa banyak kota besar zaman dulu berada di dekat sungai?",
                    "Sungai memberi air untuk minum dan bertani, tanah di sekitarnya subur, dan sungai bisa dipakai sebagai jalur transportasi dan perdagangan.",
                ),
            ),
            quiz = listOf(
                question(
                    "Apa yang menjadi pembatas antara masa praaksara dan masa sejarah?",
                    listOf("Penemuan api", "Dikenalnya tulisan", "Penemuan roda", "Munculnya pertanian"),
                    1,
                    "Masa sejarah dimulai ketika manusia mengenal tulisan, karena sejak itu ada catatan tertulis tentang kehidupan mereka.",
                ),
                question(
                    "Peradaban Mesopotamia tumbuh di antara dua sungai, yaitu…",
                    listOf("Tigris dan Eufrat", "Nil dan Indus", "Gangga dan Indus", "Huang He dan Nil"),
                    0,
                    "Mesopotamia berarti \"tanah di antara sungai-sungai\", yaitu Sungai Tigris dan Eufrat.",
                ),
                question(
                    "Prasasti Yupa, bukti tertulis tertua di Indonesia, berasal dari kerajaan…",
                    listOf("Majapahit", "Sriwijaya", "Kutai", "Tarumanegara"),
                    2,
                    "Prasasti Yupa berasal dari Kerajaan Kutai di Kalimantan Timur dan ditulis dengan huruf Pallawa berbahasa Sanskerta.",
                ),
                question(
                    "Aksara paku dikembangkan oleh bangsa…",
                    listOf("Mesir Kuno", "Romawi", "Tiongkok", "Sumeria"),
                    3,
                    "Aksara paku dikembangkan bangsa Sumeria di Mesopotamia. Bangsa Mesir Kuno mengembangkan hieroglif.",
                ),
            ),
        ),
        SeedLesson(
            id = "s2-l2",
            title = "Mengenal kerajaan-kerajaan besar di Indonesia",
            summary = "Menelusuri kerajaan Hindu-Buddha dan Islam di Nusantara—dari Kutai, Sriwijaya, dan Majapahit hingga Demak dan Ternate.",
            widgetTeaser = "Sumpah Palapa diucapkan Gajah Mada, mahapatih Majapahit, sebagai tekad mempersatukan Nusantara.",
            sections = listOf(
                kenali(
                    "Kerajaan di Nusantara",
                    """
                    Sebelum ada negara Indonesia, wilayah Nusantara dipimpin oleh banyak kerajaan. Secara umum, kerajaan-kerajaan ini dibagi menjadi dua kelompok: kerajaan bercorak Hindu-Buddha yang berkembang lebih dulu, lalu kerajaan bercorak Islam.

                    Pengaruh agama dan budaya itu datang melalui perdagangan, karena Nusantara berada di jalur laut yang ramai antara India, Tiongkok, dan Timur Tengah.
                    """,
                ),
                ketahui(
                    "Kerajaan Hindu-Buddha",
                    """
                    • Kutai (Kalimantan Timur), sekitar abad ke-4–5 Masehi, dikenal lewat prasasti Yupa.
                    • Tarumanegara (Jawa Barat), dengan rajanya yang terkenal, Purnawarman.
                    • Sriwijaya (Sumatra Selatan), kerajaan maritim bercorak Buddha yang berjaya sekitar abad ke-7 dan menguasai perdagangan di Selat Malaka.
                    • Mataram Kuno (Jawa Tengah), masa dibangunnya Candi Borobudur (Buddha) dan Candi Prambanan (Hindu) sekitar abad ke-8–9.
                    • Majapahit (Jawa Timur), berdiri tahun 1293 dan mencapai puncak kejayaan pada masa Raja Hayam Wuruk dan Mahapatih Gajah Mada.
                    """,
                ),
                ketahui(
                    "Kerajaan Islam",
                    """
                    Mulai sekitar abad ke-13, kerajaan bercorak Islam tumbuh, terutama di daerah pesisir:

                    • Samudra Pasai (Aceh), salah satu kerajaan Islam pertama di Nusantara.
                    • Demak (Jawa Tengah), kerajaan Islam pertama di Pulau Jawa.
                    • Ternate dan Tidore (Maluku Utara), kaya akan rempah-rempah seperti cengkih.
                    • Gowa-Tallo (Sulawesi Selatan), kekuatan maritim di wilayah timur Nusantara.

                    Rempah-rempah dari Maluku inilah yang kemudian menarik bangsa-bangsa Eropa datang ke Nusantara.
                    """,
                ),
                hubungkan(
                    "Jejaknya di sekitar kita",
                    """
                    Jejak kerajaan-kerajaan itu masih terasa hari ini. Semboyan negara kita, Bhinneka Tunggal Ika, diambil dari Kitab Sutasoma karya Mpu Tantular pada masa Majapahit. Candi Borobudur dan Prambanan menjadi tujuan wisata dunia. Nama Sriwijaya dan Gajah Mada dipakai untuk universitas, jalan, dan stadion.

                    Kalau kamu berkunjung ke suatu daerah, coba cari tahu kerajaan apa yang dulu pernah ada di sana—itu bisa menjadi bahan obrolan yang menarik.
                    """,
                ),
                rangkum(
                    """
                    • Kerajaan di Nusantara terbagi menjadi kerajaan bercorak Hindu-Buddha dan Islam.
                    • Sriwijaya adalah kerajaan maritim besar di Sumatra; Majapahit berjaya di masa Hayam Wuruk dan Gajah Mada.
                    • Samudra Pasai dan Demak termasuk kerajaan Islam awal; Ternate dan Tidore terkenal karena rempah.
                    • Bhinneka Tunggal Ika berasal dari Kitab Sutasoma karya Mpu Tantular.
                    """,
                ),
            ),
            keyTerms = listOf(
                term("Kerajaan maritim", "Kerajaan yang kekuatannya bertumpu pada laut, pelayaran, dan perdagangan."),
                term("Candi", "Bangunan suci peninggalan masa Hindu-Buddha, seperti Borobudur dan Prambanan."),
                term("Sumpah Palapa", "Sumpah Gajah Mada untuk tidak beristirahat menikmati kesenangan sebelum Nusantara bersatu di bawah Majapahit."),
                term("Rempah-rempah", "Bagian tumbuhan beraroma kuat, seperti cengkih dan pala, yang dulu sangat berharga dalam perdagangan dunia."),
            ),
            examples = listOf(
                "Universitas Gadjah Mada di Yogyakarta dan Universitas Sriwijaya di Sumatra Selatan memakai nama dari masa kerajaan.",
                "Cengkih yang ada di bumbu dapur adalah rempah yang dulu membuat Maluku diperebutkan bangsa-bangsa Eropa.",
            ),
            prompts = listOf(
                prompt(
                    "Teman bertanya, \"Kenapa Borobudur ada di Jawa, padahal agama Buddha berasal dari India?\" Bagaimana kamu menjawab?",
                    "Karena dulu Nusantara ramai berdagang dengan India. Lewat pedagang dan para pemuka agama, Hindu dan Buddha masuk, lalu kerajaan di Jawa membangun candi seperti Borobudur sekitar abad ke-8–9.",
                ),
                prompt(
                    "Apa satu hal tentang Majapahit yang bisa kamu ceritakan?",
                    "Majapahit berjaya pada masa Hayam Wuruk dan Gajah Mada. Semboyan Bhinneka Tunggal Ika juga berasal dari kitab yang ditulis pada masa Majapahit.",
                ),
            ),
            quiz = listOf(
                question(
                    "Siapa yang mengucapkan Sumpah Palapa?",
                    listOf("Hayam Wuruk", "Gajah Mada", "Purnawarman", "Mpu Tantular"),
                    1,
                    "Sumpah Palapa diucapkan Gajah Mada, mahapatih Majapahit, sebagai tekad mempersatukan Nusantara.",
                ),
                question(
                    "Kerajaan maritim bercorak Buddha yang berpusat di Sumatra Selatan adalah…",
                    listOf("Sriwijaya", "Majapahit", "Demak", "Kutai"),
                    0,
                    "Sriwijaya berjaya sekitar abad ke-7 dan menguasai perdagangan di Selat Malaka.",
                ),
                question(
                    "Semboyan Bhinneka Tunggal Ika berasal dari kitab…",
                    listOf("Negarakertagama", "Pararaton", "Sutasoma", "Arjunawiwaha"),
                    2,
                    "Bhinneka Tunggal Ika berasal dari Kitab Sutasoma karya Mpu Tantular pada masa Majapahit.",
                ),
                question(
                    "Kerajaan Islam pertama di Pulau Jawa adalah…",
                    listOf("Mataram Kuno", "Tarumanegara", "Samudra Pasai", "Demak"),
                    3,
                    "Demak adalah kerajaan Islam pertama di Pulau Jawa. Samudra Pasai juga kerajaan Islam awal, tetapi letaknya di Aceh, Sumatra.",
                ),
            ),
        ),
    ),
)
