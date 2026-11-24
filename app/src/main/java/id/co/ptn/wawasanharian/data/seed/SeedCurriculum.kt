package id.co.ptn.wawasanharian.data.seed

/**
 * Bundled, offline curriculum. Evergreen content only (product spec §4 "Content freshness"):
 * no current office holders, prices, rankings or recent statistics.
 *
 * Increase [VERSION] whenever any content text changes so existing installs re-seed.
 */
object SeedCurriculum {
    const val VERSION = 1

    val stages: List<SeedStage> = listOf(
        stageGeografi,
        stageSejarah,
        stageSains,
        stageEkonomi,
        stageBudaya,
        stageDuniaModern,
    )
}
