package id.co.ptn.wawasanharian.data.seed

import id.co.ptn.wawasanharian.domain.SectionKind
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** Guards the content requirements from product spec §9. */
class SeedCurriculumTest {

    private val stages = SeedCurriculum.stages
    private val lessons = stages.flatMap { it.lessons }

    @Test
    fun hasSixStagesWithAtLeastTwoLessonsEach() {
        assertEquals(6, stages.size)
        stages.forEach { assertTrue("${it.id} needs ≥2 lessons", it.lessons.size >= 2) }
        assertEquals(12, lessons.size)
    }

    @Test
    fun idsAreUnique() {
        assertEquals(stages.size, stages.map { it.id }.toSet().size)
        assertEquals(lessons.size, lessons.map { it.id }.toSet().size)
        val bundle = stages.toContentBundle(SeedCurriculum.VERSION)
        listOf(
            bundle.sections.map { it.id },
            bundle.keyTerms.map { it.id },
            bundle.examples.map { it.id },
            bundle.prompts.map { it.id },
            bundle.questions.map { it.id },
            bundle.options.map { it.id },
        ).forEach { ids -> assertEquals(ids.size, ids.toSet().size) }
    }

    @Test
    fun everyLessonMeetsContentMinimums() {
        lessons.forEach { lesson ->
            val id = lesson.id
            val explanatory = lesson.sections.count { it.kind != SectionKind.RANGKUM }
            assertTrue("$id: 3–5 explanatory sections", explanatory in 3..5)
            assertEquals("$id: starts with Kenali", SectionKind.KENALI, lesson.sections.first().kind)
            assertEquals("$id: ends with Rangkum", SectionKind.RANGKUM, lesson.sections.last().kind)
            assertTrue("$id: has Hubungkan", lesson.sections.any { it.kind == SectionKind.HUBUNGKAN })
            assertTrue("$id: 3–5 key terms", lesson.keyTerms.size in 3..5)
            assertTrue("$id: ≥2 everyday examples", lesson.examples.size >= 2)
            assertTrue("$id: ≥2 conversation prompts", lesson.prompts.size >= 2)
            assertTrue("$id: 3–5 quiz questions", lesson.quiz.size in 3..5)
        }
    }

    @Test
    fun everyQuestionHasExactlyOneValidAnswerAndAnExplanation() {
        lessons.flatMap { l -> l.quiz.map { l.id to it } }.forEach { (lessonId, q) ->
            assertTrue("$lessonId: '${q.question}' needs 2–4 options", q.options.size in 2..4)
            assertTrue("$lessonId: '${q.question}' correctIndex out of range", q.correctIndex in q.options.indices)
            assertEquals("$lessonId: duplicate options", q.options.size, q.options.toSet().size)
            assertTrue("$lessonId: explanation missing", q.explanation.isNotBlank())
        }
        val bundle = stages.toContentBundle(SeedCurriculum.VERSION)
        bundle.questions.forEach { q ->
            assertEquals(1, bundle.options.count { it.questionId == q.id && it.isCorrect })
        }
    }

    @Test
    fun widgetTeasersAreShort() {
        lessons.forEach {
            assertTrue("${it.id}: teaser too long (${it.widgetTeaser.length})", it.widgetTeaser.length <= 140)
        }
    }

    @Test
    fun textHasNoBlanksOrStrayEscapes() {
        val bundle = stages.toContentBundle(SeedCurriculum.VERSION)
        val texts = buildList {
            stages.forEach { add(it.title); add(it.description) }
            bundle.lessons.forEach { add(it.title); add(it.summary); add(it.widgetTeaser) }
            bundle.sections.forEach { add(it.title); add(it.body) }
            bundle.keyTerms.forEach { add(it.term); add(it.definition) }
            bundle.examples.forEach { add(it.text) }
            bundle.prompts.forEach { add(it.prompt); add(it.exampleResponse) }
            bundle.questions.forEach { add(it.question); add(it.explanation) }
            bundle.options.forEach { add(it.label) }
        }
        texts.forEach { text ->
            assertTrue("blank text found", text.isNotBlank())
            // Raw strings do not process escapes; a backslash means an escape leaked into the UI.
            assertFalse("stray backslash in: $text", text.contains('\\'))
            assertFalse("untrimmed indentation in: $text", text.lines().any { it.startsWith("    ") })
        }
    }

    @Test
    fun contentBundleCarriesVersionAndOrder() {
        val bundle = stages.toContentBundle(contentVersion = 7)
        assertTrue(bundle.lessons.all { it.contentVersion == 7 && it.isPublished })
        assertEquals((1..6).toList(), bundle.stages.map { it.sortOrder })
        assertEquals(listOf("s1-l1", "s1-l2"), bundle.lessons.filter { it.stageId == "s1" }.sortedBy { it.sortOrder }.map { it.id })
    }
}
