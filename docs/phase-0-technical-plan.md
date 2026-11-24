# Wawasan Harian — Fase 0: Temuan & Rencana Teknis

Tanggal: 2026-11-24 · Referensi: `docs/wawasan_harian_product_spec.md`

## 1. Kondisi repositori

| Item | Nilai |
|---|---|
| Jenis proyek | Template baru Android Studio (Empty Compose Activity), belum ada kode aplikasi |
| Package / applicationId | `id.co.ptn.wawasanharian` |
| Gradle wrapper | 9.6.0 |
| Android Gradle Plugin | 9.5.2 (Kotlin bawaan AGP — tanpa plugin `kotlin-android`) |
| Kotlin / plugin Compose | 2.2.10 |
| JDK daemon Gradle | 25 (toolchain foojay); `compileOptions` Java 11 |
| compileSdk / targetSdk / minSdk | 37 / 37 / 24 |
| Compose BOM | 2026.02.01 (Material3) |
| Release build | R8 optimization aktif (`optimization { enable = true }`) |
| Git | Belum menjadi repositori git |
| Build awal | `./gradlew assembleDebug` → **BERHASIL** |

Lingkungan: SDK platform 24–37 dan build-tools 37.0.0 terpasang.

**Device uji:** TECNO CM7 (serial `140892556G003358`), Android 15 / API 35, launcher HiOS
(`com.transsion.hilauncher`). Samsung A54 yang juga tersambung **tidak** dipakai — semua perintah
diarahkan dengan `adb -s 140892556G003358` / `ANDROID_SERIAL=140892556G003358`.

## 2. Keputusan teknis

### 2.1 Widget: Jetpack Glance (bukan RemoteViews manual)
Kebutuhan widget di spesifikasi (teks, tombol, indikator progres, tap membuka pelajaran, beberapa
ukuran, state kosong/error) semuanya didukung Glance:
- `GlanceAppWidget` + `GlanceAppWidgetReceiver` → tetap widget AppWidget asli, muncul di widget picker.
- `SizeMode.Responsive` untuk tata letak kecil / sedang / besar.
- `actionStartActivity<MainActivity>(actionParametersOf(lessonId))` untuk membuka pelajaran yang tepat.
- `LinearProgressIndicator`, `ColorProvider` siang/malam, `contentDescription`.
- `provideGlance` berjalan di coroutine → bisa membaca repositori Room yang sama dengan aplikasi.
- Pembaruan: `WawasanWidget().updateAll(context)` setelah progres berubah.

Ditolak: RemoteViews manual — lebih banyak kode boilerplate tanpa kebutuhan yang tidak bisa dipenuhi Glance.

### 2.2 Arsitektur
- **Satu modul `:app`**, paket berdasarkan fitur (lihat §4). Tidak membuat paket kosong.
- **Single source of truth:** seluruh konten dan progres di **Room**. **DataStore** hanya untuk preferensi
  (`lastKnownContentVersion`, nanti pengingat). Widget membaca lewat repositori yang sama.
- **DI manual:** `AppContainer` di kelas `Application` (constructor injection). Tanpa Hilt — belum ada manfaat jelas.
- **ViewModel + StateFlow** per layar; logika bisnis di use case/repositori, bukan di Composable/widget.
- **Navigasi:** `navigation-compose`. Widget mengirim `lessonId` lewat Intent extra ke `MainActivity`
  (ditangani di `onCreate` dan `onNewIntent`).
- **Konten seed:** data Kotlin (`SeedCurriculum.kt`) yang dimasukkan ke Room saat pertama jalan atau saat
  `CONTENT_VERSION` bundel > `lastKnownContentVersion`. Hanya tabel konten yang diganti; tabel progres tidak disentuh.
  Tidak perlu parser JSON tambahan dan mudah diuji.
- **Tanpa WorkManager eksplisit di MVP.** Widget diperbarui saat: progres berubah (dipanggil dari repositori/use case),
  widget ditambahkan, dan `updatePeriodMillis` = 24 jam dari sistem. Tidak ada service yang terus berjalan.
  (Catatan: Glance sendiri memakai WorkManager secara internal sebagai dependensi transitif.)
- **Reboot:** widget dipulihkan oleh launcher; data ada di Room, jadi tidak perlu `RECEIVE_BOOT_COMPLETED`.
- **Izin:** tidak ada izin sama sekali (tidak ada INTERNET). Pengingat (POST_NOTIFICATIONS) ditunda ke Fase 6.

### 2.3 Aturan "pelajaran saat ini"
1. Pelajaran berstatus *In progress* yang paling baru dibuka, jika ada; selain itu
2. pelajaran pertama (urutan tahap → urutan pelajaran) yang belum *Completed*; selain itu
3. semua selesai → widget menampilkan pesan selamat + ajakan mengulang.

Progres ditampilkan sebagai **"Pelajaran X dari 12"**, bukan "Hari 1 dari 84" seperti contoh di spesifikasi,
karena konten MVP 12 pelajaran dan aplikasi tidak boleh mengikat pengguna ke jadwal kalender.

### 2.4 Model data
Mengikuti §8 spesifikasi, dengan penyesuaian:
- `LessonSection` diberi kolom `kind` (`KENALI`, `KETAHUI`, `HUBUNGKAN`, `DISKUSIKAN`, `RANGKUM`) untuk struktur sesi §3.
- `Lesson` ditambah `widgetTeaser` (1–2 kalimat pendek untuk widget) — terpisah dari `summary` agar muat di widget.
- `EverydayExample` (`id`, `lessonId`, `text`, `order`) — spesifikasi mewajibkan ≥2 contoh sehari-hari tapi belum punya tabelnya.
- `UserLessonProgress.status` sebagai enum `NOT_STARTED / IN_PROGRESS / COMPLETED` (disimpan sebagai teks).
- `QuizAttempt` menyimpan semua percobaan; "terbaru" dan "terbaik" diturunkan lewat query.
- Foreign key dengan `CASCADE` dari tabel anak ke `Lesson`; skema Room diekspor (`room.schemaLocation`) untuk migrasi.

## 3. Dependensi yang direncanakan

Versi stabil terbaru dicek di Google Maven / Maven Central pada 2026-11-24.

| Library | Versi | Dipakai untuk |
|---|---|---|
| `androidx.glance:glance-appwidget` | 1.2.0 | Widget |
| `androidx.glance:glance-material3` | 1.2.0 | Warna siang/malam widget |
| `androidx.room:room-runtime` / `room-ktx` / `room-compiler` | 2.8.5 | Database |
| `com.google.devtools.ksp` (plugin) | 2.3.14 | Code generation Room |
| `androidx.datastore:datastore-preferences` | 1.3.0 | Preferensi |
| `androidx.navigation:navigation-compose` | 2.10.2 | Navigasi |
| `androidx.lifecycle:lifecycle-viewmodel-compose` / `lifecycle-runtime-compose` | 2.11.1 (selaras `lifecycleRuntimeKtx` saat ini) | ViewModel, `collectAsStateWithLifecycle` |
| `org.jetbrains.kotlinx:kotlinx-coroutines-test` | 1.11.0 | Unit test |
| `androidx.room:room-testing` | 2.8.5 | Tes migrasi / in-memory DB |

Tidak ditambahkan: Hilt, Retrofit/OkHttp, kotlinx-serialization, Firebase/analytics, WorkManager eksplisit.

Versi Kotlin (2.2.10) dan Compose BOM (2026.02.01) **dipertahankan** dari template karena sudah terbukti build.
Versi terbaru yang tersedia (Kotlin 2.4.21, BOM 2026.11.01) hanya diambil jika ada dependensi yang membutuhkannya.

## 4. Struktur paket yang direncanakan

```text
id.co.ptn.wawasanharian/
├── WawasanApp.kt                 # Application + AppContainer
├── MainActivity.kt
├── navigation/
├── data/
│   ├── local/                    # Room: entity, dao, database
│   ├── datastore/
│   ├── seed/                     # SeedCurriculum.kt + seeder
│   └── repository/
├── domain/                       # model + use case (aturan progres, skor kuis, pelajaran saat ini)
├── feature/{home,lesson,quiz,curriculum,progress,settings}/
├── ui/theme/
└── widget/                       # WawasanWidget, Receiver, WidgetDataProvider, WidgetUpdater
```

## 5. Risiko

| # | Risiko | Mitigasi |
|---|---|---|
| R1 ✅ | **KSP vs Kotlin bawaan AGP 9.** _Terselesaikan di Fase 1: KSP 2.3.14 + Room 2.8.5 berhasil build dengan Kotlin 2.2.10 bawaan AGP 9.5.2._ KSP 2.2.10-2.0.2 (cocok Kotlin 2.2.10) mungkin tidak mendukung Kotlin bawaan AGP 9; seri KSP 2.3.x (terbaru 2.3.14) tidak lagi terikat versi Kotlin. | Spike di awal Fase 1: coba KSP 2.3.14 dulu. Jika gagal, coba KSP yang cocok dengan Kotlin; terakhir naikkan Kotlin. Keputusan dan hasilnya dicatat. |
| R2 | Glance 1.2.0 membawa dependensi transitif (WorkManager, protobuf) → APK lebih besar | Dapat diterima; dicek ukuran release build di Fase 5. |
| R3 | Launcher HiOS (TECNO) bisa berbeda dalam ukuran sel, resize, dan pratinjau widget | `SizeMode.Responsive` + `previewLayout` XML; uji 2×1, 4×2, 4×3 di device TECNO. |
| R4 | Penghematan baterai agresif HiOS bisa menunda update periodik | Tidak bergantung pada update periodik — widget selalu diperbarui langsung saat progres berubah. |
| R5 | Akurasi konten edukasi (12 pelajaran) | Hanya konten evergreen, tanpa statistik/pejabat saat ini; daftar klaim ditinjau sebelum masuk seed (Fase 2). |
| R6 | R8 aktif di release dapat menghapus kelas Room/Glance | Library membawa consumer rules; diverifikasi dengan `assembleRelease` + install di Fase 5. |
| R7 | Belum ada git — perubahan sulit ditinjau/dikembalikan | Disarankan `git init` + commit baseline sebelum Fase 1 (menunggu persetujuan pengguna). |

## 6. Langkah berikutnya — Fase 1 (fondasi proyek)
1. Tambah versi & library ke `libs.versions.toml`; plugin KSP (spike R1).
2. `WawasanApp` + `AppContainer`, daftarkan di manifest.
3. Tema aplikasi navy/emas (§5.3) siang & malam; dynamic color dimatikan secara default.
4. Navigasi dasar + layar placeholder untuk 6 fitur.
5. Verifikasi: `assembleDebug`, `testDebugUnitTest`, install & launch di TECNO CM7.

## 7. Perubahan keputusan selama implementasi

- **Fase 2 — DataStore ditunda.** `lastKnownContentVersion` tidak disimpan di DataStore; versi konten dibaca dari kolom
  `lesson.contentVersion` di Room sehingga pengecekan dan seeding terjadi atomik dalam satu transaksi. DataStore akan
  ditambahkan bersama fitur pengingat (opsional). `room-ktx` juga dilepas karena dukungan coroutine/Flow sudah ada di
  `room-runtime` 2.8.
- **Fase 2 — urutan pelajaran.** 12 pelajaran dari spesifikasi §9 dipetakan 2 per tahap. "Negara, bangsa, dan wilayah"
  masuk Tahap 5 (Budaya & Masyarakat); "Internet, komputer, dan AI" masuk Tahap 6 (Dunia Modern).
- **Fase 2 — tabel progres tanpa foreign key** ke `lesson`, agar penggantian konten tidak menghapus progres pengguna.
