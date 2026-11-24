package id.co.ptn.wawasanharian

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** The intent the Home Screen widget will send opens the requested lesson directly. */
@RunWith(AndroidJUnit4::class)
class LessonIntentTest {

    @get:Rule
    val rule = createEmptyComposeRule()

    @Test
    fun lessonIntentOpensThatLesson() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        ActivityScenario.launch<MainActivity>(MainActivity.lessonIntent(context, "s2-l1")).use {
            rule.waitUntil(5_000) {
                rule.onAllNodesWithText("Pelajaran 3 dari 12").fetchSemanticsNodes().isNotEmpty()
            }
            rule.onNodeWithText("Bagaimana manusia mulai mengenal tulisan dan peradaban?").assertIsDisplayed()
        }
    }

    @Test
    fun unknownLessonShowsFriendlyMessage() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        ActivityScenario.launch<MainActivity>(MainActivity.lessonIntent(context, "tidak-ada")).use {
            rule.waitUntil(5_000) {
                rule.onAllNodesWithText(
                    "Pelajaran tidak ditemukan. Mungkin isi aplikasi sudah diperbarui."
                ).fetchSemanticsNodes().isNotEmpty()
            }
        }
    }
}
