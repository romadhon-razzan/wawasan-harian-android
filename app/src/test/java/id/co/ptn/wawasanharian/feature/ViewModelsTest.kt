package id.co.ptn.wawasanharian.feature

import id.co.ptn.wawasanharian.R
import id.co.ptn.wawasanharian.domain.LessonStatus
import id.co.ptn.wawasanharian.feature.home.HomeUiState
import id.co.ptn.wawasanharian.feature.home.HomeViewModel
import id.co.ptn.wawasanharian.feature.home.greetingFor
import id.co.ptn.wawasanharian.feature.lesson.LessonBlock
import id.co.ptn.wawasanharian.feature.lesson.LessonUiState
import id.co.ptn.wawasanharian.feature.lesson.LessonViewModel
import id.co.ptn.wawasanharian.feature.quiz.QuizUiState
import id.co.ptn.wawasanharian.feature.quiz.QuizViewModel
import id.co.ptn.wawasanharian.feature.settings.SettingsViewModel
import id.co.ptn.wawasanharian.testing.FakeLearningRepository
import id.co.ptn.wawasanharian.testing.MainDispatcherRule
import id.co.ptn.wawasanharian.ui.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelsTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    private val repository = FakeLearningRepository()

    /** Keeps a WhileSubscribed StateFlow active and returns its current Success value. */
    private fun <T> TestScope.success(flow: StateFlow<UiState<T>>): () -> T {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { flow.collect {} }
        return { (flow.value as UiState.Success<T>).data }
    }

    // --- Home ---------------------------------------------------------------------------------

    @Test
    fun home_newUserSeesFirstLessonAndNoProgress() = runTest {
        val state: () -> HomeUiState = success(HomeViewModel(repository).state)
        assertEquals("l1", state().currentLesson?.id)
        assertEquals(0, state().summary.completedCount)
        assertEquals(0, state().completedLast7Days)
    }

    @Test
    fun home_updatesAfterCompletion() = runTest {
        val vm = HomeViewModel(repository, clock = { 10_000L })
        val state = success(vm.state)
        repository.markLessonCompleted("l1")
        assertEquals("l2", state().currentLesson?.id)
        assertEquals(1, state().summary.completedCount)
        assertEquals(1, state().completedLast7Days)
    }

    @Test
    fun home_allCompletedHasNoCurrentLesson() = runTest {
        val state = success(HomeViewModel(repository).state)
        repository.markLessonCompleted("l1")
        repository.markLessonCompleted("l2")
        assertNull(state().currentLesson)
        assertTrue(state().summary.allCompleted)
    }

    @Test
    fun greetingFollowsTimeOfDay() {
        assertEquals(R.string.home_greeting_morning, greetingFor(7))
        assertEquals(R.string.home_greeting_afternoon, greetingFor(12))
        assertEquals(R.string.home_greeting_evening, greetingFor(16))
        assertEquals(R.string.home_greeting_night, greetingFor(21))
        assertEquals(R.string.home_greeting_night, greetingFor(2))
    }

    // --- Lesson -------------------------------------------------------------------------------

    @Test
    fun lesson_openingMarksItInProgress() = runTest {
        val state: () -> LessonUiState? = success(LessonViewModel("l1", repository).state)
        assertEquals(listOf("l1"), repository.openedLessons)
        assertEquals(LessonStatus.IN_PROGRESS, state()?.detail?.lesson?.status)
    }

    @Test
    fun lesson_unknownIdIsNotFoundAndNotRecorded() = runTest {
        val state = success(LessonViewModel("missing", repository).state)
        assertNull(state())
        assertTrue(repository.openedLessons.isEmpty())
    }

    @Test
    fun lesson_markCompleted() = runTest {
        val vm = LessonViewModel("l1", repository)
        val state = success(vm.state)
        vm.markCompleted()
        assertEquals(LessonStatus.COMPLETED, state()?.detail?.lesson?.status)
    }

    @Test
    fun lesson_blocksFollowSessionStructure() = runTest {
        val state = success(LessonViewModel("l1", repository).state)
        val kinds = state()!!.blocks.map {
            when (it) {
                is LessonBlock.Section -> "section${it.step}"
                is LessonBlock.KeyTerms -> "terms"
                is LessonBlock.Examples -> "examples"
                is LessonBlock.Discussion -> "discussion"
                is LessonBlock.Quiz -> "quiz"
            }
        }
        assertEquals(
            listOf("section1", "section2", "terms", "section3", "examples", "discussion", "quiz", "section6"),
            kinds,
        )
    }

    // --- Quiz ---------------------------------------------------------------------------------

    @Test
    fun quiz_cannotSubmitUntilAllAnswered() = runTest {
        val vm = QuizViewModel("l1", repository)
        val state: () -> QuizUiState = success(vm.state)
        vm.select("l1-q1", "l1-q1-a")
        assertFalse(state().canSubmit)
        vm.submit()
        assertNull(state().result)
        assertEquals(0, repository.attemptCount())
    }

    @Test
    fun quiz_submitScoresStoresAndLocksAnswers() = runTest {
        val vm = QuizViewModel("l1", repository)
        val state = success(vm.state)
        vm.select("l1-q1", "l1-q1-a") // correct
        vm.select("l1-q2", "l1-q2-a") // wrong
        assertTrue(state().canSubmit)
        vm.submit()

        assertEquals(1, state().result?.score)
        assertEquals(2, state().result?.total)
        assertEquals(1, repository.attemptCount())
        assertEquals(1, state().best?.score)

        vm.select("l1-q2", "l1-q2-b") // ignored after submit
        assertEquals("l1-q2-a", state().answers["l1-q2"])
    }

    @Test
    fun quiz_retryClearsAnswersAndKeepsBestScore() = runTest {
        val vm = QuizViewModel("l1", repository)
        val state = success(vm.state)
        vm.select("l1-q1", "l1-q1-a")
        vm.select("l1-q2", "l1-q2-b")
        vm.submit()
        vm.retry()
        assertNull(state().result)
        assertTrue(state().answers.isEmpty())
        assertEquals(2, state().best?.score)
    }

    @Test
    fun quiz_doesNotCompleteLesson() = runTest {
        val vm = QuizViewModel("l1", repository)
        success(vm.state)
        vm.select("l1-q1", "l1-q1-a")
        vm.select("l1-q2", "l1-q2-b")
        vm.submit()
        val status = repository.observeCurriculum().first().first().lessons.first { it.id == "l1" }.status
        assertEquals(LessonStatus.NOT_STARTED, status)
    }

    // --- Settings -----------------------------------------------------------------------------

    @Test
    fun settings_resetClearsProgressAndSignalsOnce() = runTest {
        repository.markLessonCompleted("l1")
        val vm = SettingsViewModel(repository)
        vm.resetProgress()
        assertTrue(vm.resetDone.value)
        val lessons = repository.observeCurriculum().first().first().lessons
        assertTrue(lessons.all { it.status == LessonStatus.NOT_STARTED })
        vm.onResetMessageShown()
        assertFalse(vm.resetDone.value)
    }
}
