# Wawasan Harian

Aplikasi Android untuk belajar pengetahuan umum sedikit demi sedikit setiap hari, dengan
**widget Home Screen** sebagai pengalaman utama. Spesifikasi produk: [`docs/wawasan_harian_product_spec.md`](docs/wawasan_harian_product_spec.md).
Keputusan teknis: [`docs/phase-0-technical-plan.md`](docs/phase-0-technical-plan.md). Tinjauan konten: [`docs/content-review.md`](docs/content-review.md).

## Status

| Fase | Status |
|---|---|
| 0 — Inspeksi & keputusan teknis | Selesai |
| 1 — Fondasi proyek (tema, navigasi, dependensi) | Selesai |
| 2 — Data lokal & kurikulum awal | Selesai |
| 3 — Pengalaman belajar | Selesai |
| 4 — Widget Home Screen (Glance) | Berikutnya |
| 5 — Polesan & kualitas | – |

## Kebutuhan

- Android Studio dengan Android SDK 37 (compileSdk/targetSdk 37, minSdk 24)
- JDK untuk Gradle disediakan otomatis lewat toolchain (JDK 25)
- Stack: AGP 9.5.2 (Kotlin bawaan) · Kotlin 2.2.10 · Compose · Room 2.8.5 (KSP 2.3.14) · Navigation Compose 2.10.2

## Build, install, dan test

Build debug memakai applicationId `id.co.ptn.wawasanharian.debug` sehingga bisa terpasang berdampingan dengan build release.

```bash
./gradlew assembleDebug              # build APK debug
./gradlew testDebugUnitTest          # unit test (JVM)

# Jika lebih dari satu device tersambung, pilih device dengan ANDROID_SERIAL
ANDROID_SERIAL=<serial> ./gradlew installDebug
ANDROID_SERIAL=<serial> ./gradlew connectedDebugAndroidTest   # UI test di device
```

Membuka pelajaran tertentu lewat intent (jalur yang dipakai widget):

```bash
adb shell am start -n id.co.ptn.wawasanharian.debug/id.co.ptn.wawasanharian.MainActivity \
  --es id.co.ptn.wawasanharian.extra.LESSON_ID <lessonId>
```

## Menambahkan widget

Widget dibangun di Fase 4. Petunjuk menambahkan widget dari launcher akan ditulis di sini.

## Izin

Aplikasi tidak meminta izin apa pun (tidak ada internet, lokasi, kontak, kamera, atau mikrofon).
