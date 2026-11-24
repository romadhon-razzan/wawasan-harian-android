package id.co.ptn.wawasanharian.feature.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.ptn.wawasanharian.domain.ConversationPrompt
import id.co.ptn.wawasanharian.domain.KeyTerm
import id.co.ptn.wawasanharian.domain.LearningRepository
import id.co.ptn.wawasanharian.domain.LessonDetail
import id.co.ptn.wawasanharian.domain.LessonSection
import id.co.ptn.wawasanharian.domain.QuizStats
import id.co.ptn.wawasanharian.domain.SectionKind
import id.co.ptn.wawasanharian.ui.UiState
import id.co.ptn.wawasanharian.ui.asUiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/** Ordered blocks of a lesson page, following Kenali → Ketahui → Hubungkan → Diskusikan → Uji → Rangkum. */
sealed interface LessonBlock {
    /** [step] is the 1-based position in the six-step session structure. */
    data class Section(val section: LessonSection, val step: Int) : LessonBlock
    data class KeyTerms(val terms: List<KeyTerm>) : LessonBlock
    data class Examples(val examples: List<String>) : LessonBlock
    data class Discussion(val prompts: List<ConversationPrompt>, val step: Int = 4) : LessonBlock
    data class Quiz(val questionCount: Int, val step: Int = 5) : LessonBlock
}

/**
 * Key terms follow the last "Ketahui" section, everyday examples follow "Hubungkan",
 * then discussion and quiz, and the "Rangkum" recap always comes last.
 */
fun lessonBlocks(detail: LessonDetail): List<LessonBlock> {
    val body = detail.sections.filter { it.kind != SectionKind.RANGKUM }
    val recap = detail.sections.filter { it.kind == SectionKind.RANGKUM }
    val lastKetahui = body.indexOfLast { it.kind == SectionKind.KETAHUI }
    val lastHubungkan = body.indexOfLast { it.kind == SectionKind.HUBUNGKAN }

    return buildList {
        body.forEachIndexed { index, section ->
            add(LessonBlock.Section(section, stepOf(section.kind)))
            if (index == lastKetahui && detail.keyTerms.isNotEmpty()) add(LessonBlock.KeyTerms(detail.keyTerms))
            if (index == lastHubungkan && detail.everydayExamples.isNotEmpty()) add(LessonBlock.Examples(detail.everydayExamples))
        }
        if (lastKetahui < 0 && detail.keyTerms.isNotEmpty()) add(LessonBlock.KeyTerms(detail.keyTerms))
        if (lastHubungkan < 0 && detail.everydayExamples.isNotEmpty()) add(LessonBlock.Examples(detail.everydayExamples))
        if (detail.conversationPrompts.isNotEmpty()) add(LessonBlock.Discussion(detail.conversationPrompts))
        if (detail.questionCount > 0) add(LessonBlock.Quiz(detail.questionCount))
        recap.forEach { add(LessonBlock.Section(it, stepOf(it.kind))) }
    }
}

private fun stepOf(kind: SectionKind): Int = when (kind) {
    SectionKind.KENALI -> 1
    SectionKind.KETAHUI -> 2
    SectionKind.HUBUNGKAN -> 3
    SectionKind.RANGKUM -> 6
}

data class LessonUiState(
    val detail: LessonDetail,
    val blocks: List<LessonBlock>,
    val quizStats: QuizStats,
)

class LessonViewModel(
    private val lessonId: String,
    private val repository: LearningRepository,
) : ViewModel() {

    /** Success(null) means the lesson does not exist. */
    val state: StateFlow<UiState<LessonUiState?>> = combine(
        repository.observeLessonDetail(lessonId),
        repository.observeQuizStats(lessonId),
    ) { detail, stats ->
        detail?.let { LessonUiState(it, lessonBlocks(it), stats) }
    }.asUiState(viewModelScope)

    init {
        viewModelScope.launch {
            // Only record real lessons, so a stale link never creates orphan progress.
            if (repository.observeLessonDetail(lessonId).first() != null) {
                repository.markLessonOpened(lessonId)
            }
        }
    }

    fun markCompleted() {
        viewModelScope.launch { repository.markLessonCompleted(lessonId) }
    }
}
