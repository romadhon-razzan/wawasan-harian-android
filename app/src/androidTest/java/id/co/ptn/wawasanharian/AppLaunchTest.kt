package id.co.ptn.wawasanharian

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppLaunchTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun appLaunches_showsBottomNavigation() {
        listOf("Beranda", "Kurikulum", "Progres", "Pengaturan").forEach { label ->
            composeRule.onNode(hasText(label) and isSelectable()).assertIsDisplayed()
        }
    }

    @Test
    fun bottomNavigation_switchesTabs() {
        composeRule.onNode(hasText("Kurikulum") and isSelectable()).performClick()
        composeRule.onNodeWithText(
            "Enam tahap pengetahuan umum, dimulai dari dasar. Buka pelajaran mana pun, kapan pun."
        ).assertIsDisplayed()

        composeRule.onNode(hasText("Pengaturan") and isSelectable()).performClick()
        composeRule.onNodeWithText("Reset progres belajar").assertIsDisplayed()
    }
}
