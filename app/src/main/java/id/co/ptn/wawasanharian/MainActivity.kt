package id.co.ptn.wawasanharian

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import id.co.ptn.wawasanharian.navigation.WawasanNavHost
import id.co.ptn.wawasanharian.ui.theme.WawasanHarianTheme

class MainActivity : ComponentActivity() {

    /** Lesson requested via intent (e.g. from the widget), consumed once by navigation. */
    private var pendingLessonId by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (savedInstanceState == null) {
            pendingLessonId = intent.getStringExtra(EXTRA_LESSON_ID)
        }
        setContent {
            WawasanHarianTheme {
                WawasanNavHost(
                    pendingLessonId = pendingLessonId,
                    onPendingLessonHandled = { pendingLessonId = null },
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        intent.getStringExtra(EXTRA_LESSON_ID)?.let { pendingLessonId = it }
    }

    companion object {
        const val EXTRA_LESSON_ID = "id.co.ptn.wawasanharian.extra.LESSON_ID"

        fun lessonIntent(context: Context, lessonId: String): Intent =
            Intent(context, MainActivity::class.java)
                .putExtra(EXTRA_LESSON_ID, lessonId)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
    }
}
