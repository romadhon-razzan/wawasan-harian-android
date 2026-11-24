package id.co.ptn.wawasanharian

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import id.co.ptn.wawasanharian.domain.LearningRules
import id.co.ptn.wawasanharian.domain.LessonStatus
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** End-to-end flows on the real app database (product spec §13 instrumentation tests). */
@RunWith(AndroidJUnit4::class)
class LearningFlowTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    private val repository
        get() = (ApplicationProvider.getApplicationContext<Context>() as WawasanApp).container.learningRepository

    @Before
    fun resetProgress() {
        runBlocking { repository.resetProgress() }
        rule.waitForIdle()
    }

    private fun scrollTo(text: String) {
        rule.onNode(hasScrollToNodeAction()).performScrollToNode(hasText(text))
    }

    private fun scrollToAndClick(text: String) {
        scrollTo(text)
        rule.onNodeWithText(text).performClick()
        rule.waitForIdle()
    }

    private fun waitForText(text: String) {
        rule.waitUntil(5_000) { rule.onAllNodesWithText(text).fetchSemanticsNodes().isNotEmpty() }
    }

    private fun status(lessonId: String): LessonStatus = runBlocking {
        repository.observeCurriculum().first().flatMap { it.lessons }.first { it.id == lessonId }.status
    }

    private fun completedCount(): Int = runBlocking {
        LearningRules.progressSummary(repository.observeCurriculum().first()).completedCount
    }

    @Test
    fun userCanOpenTheCurrentLessonFromHome() {
        rule.onNodeWithText("Mulai belajar").performClick()
        waitForText("Pelajaran 1 dari 12")
        rule.onNodeWithText("Apa itu Bumi, benua, dan samudra?").assertIsDisplayed()
        rule.waitUntil(5_000) { status("s1-l1") == LessonStatus.IN_PROGRESS }
    }

    @Test
    fun userCanCompleteALesson_andHomeMovesOn() {
        rule.onNodeWithText("Mulai belajar").performClick()
        waitForText("Pelajaran 1 dari 12")
        scrollToAndClick("Tandai selesai")
        waitForText("Pelajaran ini sudah selesai")
        assertEquals(LessonStatus.COMPLETED, status("s1-l1"))

        rule.onNodeWithContentDescription("Kembali").performClick()
        waitForText("1 dari 12 pelajaran selesai")
        rule.onNodeWithText("Mengenal Indonesia dan negara-negara tetangga").assertIsDisplayed()
    }

    @Test
    fun quizShowsScoreAndExplanations_andRetryResets() {
        rule.onNodeWithText("Mulai belajar").performClick()
        waitForText("Pelajaran 1 dari 12")
        scrollToAndClick("Mulai kuis")
        waitForText("Soal 1 dari 4")

        scrollToAndClick("Samudra Atlantik")   // wrong
        scrollToAndClick("Hindia dan Pasifik") // correct
        scrollToAndClick("Asia")               // correct
        scrollToAndClick("Sekitar 71%")        // correct
        scrollToAndClick("Lihat hasil")

        waitForText("Skor kamu: 3 dari 4")
        scrollTo("Belum tepat. Jawaban yang benar: Samudra Pasifik")
        rule.onNodeWithText("Samudra Pasifik adalah samudra terbesar sekaligus terdalam di dunia.").assertIsDisplayed()

        rule.onAllNodesWithText("Coba lagi")[0].performClick()
        rule.waitUntil(5_000) { rule.onAllNodesWithText("Skor kamu: 3 dari 4").fetchSemanticsNodes().isEmpty() }
        scrollTo("Lihat hasil")
        rule.onNodeWithText("0 dari 4 soal terjawab").assertIsDisplayed()

        // The quiz never completes the lesson by itself.
        assertNotEquals(LessonStatus.COMPLETED, status("s1-l1"))
    }

    @Test
    fun resetRequiresConfirmation() {
        runBlocking { repository.markLessonCompleted("s1-l1") }
        rule.onNode(hasText("Pengaturan") and isSelectable()).performClick()

        rule.onNodeWithText("Reset progres belajar").performClick()
        rule.onNodeWithText("Reset progres?").assertIsDisplayed()
        rule.onNodeWithText("Batal").performClick()
        rule.waitForIdle()
        assertEquals(1, completedCount())

        rule.onNodeWithText("Reset progres belajar").performClick()
        rule.onNodeWithText("Reset").performClick()
        rule.waitUntil(5_000) { completedCount() == 0 }
        waitForText("Progres belajar telah direset.")
    }

    @Test
    fun progressRemainsAfterActivityRestart() {
        runBlocking { repository.markLessonCompleted("s1-l1") }
        waitForText("1 dari 12 pelajaran selesai")
        rule.activityRule.scenario.recreate()
        waitForText("1 dari 12 pelajaran selesai")
        rule.onNodeWithText("1 dari 12 pelajaran selesai").assertIsDisplayed()
    }
}
