package id.co.ptn.wawasanharian.data.seed

internal val stageEkonomi = SeedStage(
    id = "s4",
    title = "Ekonomi dan Pemerintahan",
    description = "Memahami uang, harga, dan lembaga yang menjaga perekonomian—bekal untuk mengikuti obrolan tentang ekonomi.",
    lessons = listOf(
        SeedLesson(
            id = "s4-l1",
            title = "Apa itu uang dan bagaimana cara kerjanya?",
            summary = "Mengenal perjalanan dari barter ke uang, fungsi uang, jenis-jenisnya, dan peran Bank Indonesia.",
            widgetTeaser = "Uang punya tiga fungsi utama: alat tukar, satuan hitung, dan penyimpan nilai.",
            sections = listOf(
                kenali(
                    "Dari barter ke uang",
                    """
                    Sebelum ada uang, orang bertukar barang secara langsung. Cara ini disebut barter, misalnya beras ditukar dengan ikan.

                    Barter punya kesulitan. Kamu harus menemukan orang yang memiliki barang yang kamu butuhkan dan sekaligus membutuhkan barangmu. Selain itu, sulit menentukan berapa ikan yang setara dengan satu karung beras. Karena itulah manusia mulai memakai benda yang disepakati bersama sebagai alat tukar—cikal bakal uang.
                    """,
                ),
                ketahui(
                    "Tiga fungsi uang",
                    """
                    • Alat tukar: kita membeli barang dan jasa dengan uang, tanpa perlu barter.
                    • Satuan hitung: harga semua barang bisa dinyatakan dalam satuan yang sama, misalnya rupiah, sehingga mudah dibandingkan.
                    • Penyimpan nilai: uang bisa disimpan untuk dipakai di kemudian hari.

                    Uang yang kita pakai sekarang tidak dijamin dengan emas. Nilainya berasal dari kepercayaan masyarakat dan ketetapan hukum bahwa uang itu sah sebagai alat pembayaran.
                    """,
                ),
                ketahui(
                    "Jenis uang dan siapa yang mengaturnya",
                    """
                    • Uang kartal: uang kertas dan uang logam yang bisa kita pegang.
                    • Uang giral: uang dalam rekening bank yang dapat dipindahkan, misalnya lewat transfer atau cek.
                    • Uang elektronik: nilai uang yang disimpan dalam kartu atau aplikasi.

                    Di Indonesia, lembaga yang berwenang mengeluarkan rupiah adalah Bank Indonesia, bank sentral negara kita. Bank Indonesia juga bertugas menjaga nilai rupiah agar tetap stabil.
                    """,
                ),
                hubungkan(
                    "Uang dalam keseharian",
                    """
                    Saat kamu membayar dengan memindai QRIS atau mentransfer, kamu memakai uang giral atau uang elektronik. Tidak ada lembaran uang yang berpindah tangan, tetapi nilainya tetap berpindah.

                    Bank menerima simpanan dari masyarakat dan menyalurkannya sebagai pinjaman. Bank memberi bunga kepada penabung dan mengenakan bunga kepada peminjam; selisihnya menjadi salah satu sumber pendapatan bank.
                    """,
                ),
                rangkum(
                    """
                    • Uang lahir untuk mengatasi kesulitan barter.
                    • Fungsi uang: alat tukar, satuan hitung, dan penyimpan nilai.
                    • Jenis uang: kartal (fisik), giral (rekening), dan elektronik.
                    • Bank Indonesia mengeluarkan rupiah dan menjaga nilainya tetap stabil.
                    """,
                ),
            ),
            keyTerms = listOf(
                term("Barter", "Pertukaran barang dengan barang secara langsung, tanpa uang."),
                term("Bank sentral", "Lembaga yang mengeluarkan uang dan menjaga kestabilan nilai mata uang suatu negara. Di Indonesia: Bank Indonesia."),
                term("Uang giral", "Uang dalam bentuk saldo rekening bank yang bisa dipindahkan, misalnya lewat transfer."),
                term("Bunga", "Imbalan atas uang yang disimpan atau biaya atas uang yang dipinjam, biasanya dalam persen."),
            ),
            examples = listOf(
                "Membandingkan harga dua merek sabun di toko daring menunjukkan fungsi uang sebagai satuan hitung.",
                "Menyisihkan uang di tabungan untuk biaya sekolah anak tahun depan menunjukkan fungsi uang sebagai penyimpan nilai.",
            ),
            prompts = listOf(
                prompt(
                    "Kalau ditanya, \"Kenapa uang kertas berharga padahal cuma kertas?\", apa jawabanmu?",
                    "Karena kita semua percaya dan negara menetapkannya sebagai alat bayar yang sah. Bank Indonesia menjaga nilainya supaya kepercayaan itu tetap terjaga.",
                ),
                prompt(
                    "Apa bedanya membayar tunai dengan membayar lewat transfer?",
                    "Bayar tunai memakai uang kartal, yaitu uang fisik. Transfer memakai uang giral, yaitu saldo di rekening bank yang dipindahkan secara elektronik.",
                ),
            ),
            quiz = listOf(
                question(
                    "Apa kesulitan utama dalam sistem barter?",
                    listOf(
                        "Harus menemukan orang yang saling membutuhkan barang satu sama lain",
                        "Barang tidak bisa dipindahkan",
                        "Barter dilarang oleh pemerintah",
                        "Tidak ada barang yang bisa ditukar",
                    ),
                    0,
                    "Dalam barter, kedua pihak harus kebetulan saling membutuhkan barang milik pihak lain. Uang menghapus kesulitan ini.",
                ),
                question(
                    "Lembaga yang berwenang mengeluarkan rupiah adalah…",
                    listOf("Kementerian Keuangan", "Bank Indonesia", "Otoritas Jasa Keuangan", "Bank umum"),
                    1,
                    "Bank Indonesia adalah bank sentral yang berwenang mengeluarkan rupiah dan menjaga kestabilan nilainya.",
                ),
                question(
                    "Saldo di rekening bank yang dipindahkan lewat transfer termasuk…",
                    listOf("Uang kartal", "Uang logam", "Uang giral", "Barter"),
                    2,
                    "Uang giral adalah uang dalam rekening bank. Uang kartal adalah uang fisik, yaitu kertas dan logam.",
                ),
                question(
                    "Membandingkan harga barang dalam rupiah menunjukkan fungsi uang sebagai…",
                    listOf("Penyimpan nilai", "Alat tukar", "Jaminan utang", "Satuan hitung"),
                    3,
                    "Sebagai satuan hitung, uang membuat harga berbagai barang bisa dinyatakan dan dibandingkan dalam satuan yang sama.",
                ),
            ),
        ),
        SeedLesson(
            id = "s4-l2",
            title = "Apa itu inflasi dan mengapa harga barang naik?",
            summary = "Memahami inflasi, cara mengukurnya, penyebabnya, dan dampaknya terhadap uang kita.",
            widgetTeaser = "Inflasi adalah kenaikan harga barang dan jasa secara umum dan terus-menerus—bukan hanya satu barang yang naik.",
            sections = listOf(
                kenali(
                    "Apa itu inflasi?",
                    """
                    Inflasi adalah kenaikan harga barang dan jasa secara umum dan terus-menerus dalam periode tertentu. Kata kuncinya "secara umum": kalau hanya harga cabai yang naik, itu belum tentu inflasi. Disebut inflasi jika harga banyak barang naik bersama-sama.

                    Akibat inflasi, uang dengan jumlah yang sama hanya bisa membeli lebih sedikit barang. Dengan kata lain, daya beli uang menurun.
                    """,
                ),
                ketahui(
                    "Bagaimana inflasi diukur?",
                    """
                    Di Indonesia, Badan Pusat Statistik (BPS) mengukur inflasi dengan Indeks Harga Konsumen (IHK). IHK memantau harga sekumpulan barang dan jasa yang umum dibeli rumah tangga, seperti beras, minyak goreng, sewa rumah, dan transportasi.

                    Jika IHK sekarang lebih tinggi daripada periode sebelumnya, berarti terjadi inflasi. Persentase kenaikannya disebut tingkat inflasi.

                    Kebalikan dari inflasi adalah deflasi, yaitu turunnya harga barang dan jasa secara umum.
                    """,
                ),
                ketahui(
                    "Mengapa harga naik?",
                    """
                    Beberapa penyebab inflasi:

                    • Permintaan naik: ketika banyak orang ingin membeli sementara barang terbatas, harga cenderung naik.
                    • Biaya produksi naik: misalnya harga bahan bakar atau bahan baku naik, sehingga produsen menaikkan harga jual.
                    • Jumlah uang beredar bertambah terlalu cepat dibandingkan barang yang tersedia.

                    Bank Indonesia berusaha menjaga inflasi tetap rendah dan stabil, salah satunya dengan mengatur suku bunga acuan.
                    """,
                ),
                hubungkan(
                    "Inflasi dan dompet kita",
                    """
                    Inflasi yang rendah dan stabil dianggap wajar dalam ekonomi yang tumbuh. Yang menjadi masalah adalah inflasi yang tinggi atau tidak terkendali, karena gaji dan tabungan bisa kalah cepat dibanding kenaikan harga.

                    Itu sebabnya orang sering membandingkan bunga tabungan atau hasil investasi dengan tingkat inflasi. Jika bunga tabungan lebih kecil daripada inflasi, daya beli uang yang ditabung sebenarnya menurun.
                    """,
                ),
                rangkum(
                    """
                    • Inflasi adalah kenaikan harga secara umum dan terus-menerus, sehingga daya beli turun.
                    • BPS mengukur inflasi dengan Indeks Harga Konsumen (IHK).
                    • Penyebabnya antara lain permintaan naik, biaya produksi naik, dan uang beredar bertambah terlalu cepat.
                    • Deflasi adalah kebalikan inflasi: harga turun secara umum.
                    """,
                ),
            ),
            keyTerms = listOf(
                term("Inflasi", "Kenaikan harga barang dan jasa secara umum dan terus-menerus."),
                term("Daya beli", "Banyaknya barang dan jasa yang bisa dibeli dengan sejumlah uang."),
                term("Indeks Harga Konsumen (IHK)", "Ukuran perubahan harga sekumpulan barang dan jasa yang umum dibeli rumah tangga."),
                term("Deflasi", "Penurunan harga barang dan jasa secara umum."),
            ),
            examples = listOf(
                "Uang belanja yang dulu cukup untuk membeli sayur selama seminggu, beberapa tahun kemudian mungkin hanya cukup untuk beberapa hari.",
                "Ketika harga bahan bakar naik, ongkos angkutan dan harga barang yang diangkut sering ikut naik.",
            ),
            prompts = listOf(
                prompt(
                    "Teman bilang, \"Harga cabai naik, berarti inflasi tinggi!\" Bagaimana menanggapinya?",
                    "Belum tentu. Inflasi dilihat dari harga banyak barang secara umum. Kalau hanya cabai yang naik, bisa jadi karena gagal panen atau permintaan musiman.",
                ),
                prompt(
                    "Kenapa menabung saja kadang tidak cukup?",
                    "Karena kalau bunga tabungan lebih kecil daripada inflasi, daya beli uang kita pelan-pelan menurun.",
                ),
            ),
            quiz = listOf(
                question(
                    "Manakah yang paling tepat menggambarkan inflasi?",
                    listOf(
                        "Naiknya harga satu jenis barang",
                        "Kenaikan harga barang dan jasa secara umum dan terus-menerus",
                        "Turunnya jumlah uang beredar",
                        "Naiknya gaji pegawai",
                    ),
                    1,
                    "Inflasi adalah kenaikan harga secara umum dan terus-menerus, bukan kenaikan harga satu barang saja.",
                ),
                question(
                    "Lembaga yang mengukur inflasi di Indonesia melalui IHK adalah…",
                    listOf("Badan Pusat Statistik (BPS)", "Bank Indonesia", "Kementerian Perdagangan", "Otoritas Jasa Keuangan"),
                    0,
                    "BPS mengukur inflasi dengan Indeks Harga Konsumen. Bank Indonesia berperan menjaga agar inflasi tetap rendah dan stabil.",
                ),
                question(
                    "Apa dampak inflasi yang tinggi terhadap uang yang kita simpan?",
                    listOf("Daya belinya meningkat", "Tidak berpengaruh sama sekali", "Daya belinya menurun", "Jumlah uangnya bertambah sendiri"),
                    2,
                    "Saat harga naik, uang dengan jumlah yang sama bisa membeli lebih sedikit barang. Artinya, daya belinya menurun.",
                ),
                question(
                    "Turunnya harga barang dan jasa secara umum disebut…",
                    listOf("Resesi", "Devaluasi", "Stagnasi", "Deflasi"),
                    3,
                    "Deflasi adalah kebalikan dari inflasi, yaitu penurunan harga barang dan jasa secara umum.",
                ),
            ),
        ),
    ),
)
