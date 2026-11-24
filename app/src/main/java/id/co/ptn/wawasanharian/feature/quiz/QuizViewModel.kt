package id.co.ptn.wawasanharian.feature.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.ptn.wawasanharian.domain.LearningRepository
import id.co.ptn.wawasanharian.domain.QuizAttempt
import id.co.ptn.wawasanharian.domain.QuizQuestion
import id.co.ptn.wawasanharian.domain.QuizResult
import id.co.ptn.wawasanharian.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class QuizUiState(
    val lessonTitle: String,
    val questions: List<QuizQuestion>,
    /** questionId → selected optionId */
    val answers: Map<String, String>,
    /** Non-null once submitted; answers are then locked until [QuizViewModel.retry]. */
    val result: QuizResult?,
    val best: QuizAttempt?,
) {
    val answeredCount: Int get() = questions.count { it.id in answers }
    val canSubmit: Boolean get() = result == null && questions.isNotEmpty() && answeredCount == questions.size
}

class QuizViewModel(
    private val lessonId: String,
    private val repository: LearningRepository,
) : ViewModel() {

    private sealed interface Questions {
        data object Loading : Questions
        data class Loaded(val items: List<QuizQuestion>) : Questions
        data object Failed : Questions
    }

    private val questions = MutableStateFlow<Questions>(Questions.Loading)
    private val answers = MutableStateFlow<Map<String, String>>(emptyMap())
    private val result = MutableStateFlow<QuizResult?>(null)
    private var submitting = false

    val state: StateFlow<UiState<QuizUiState>> = combine(
        questions,
        answers,
        result,
        repository.observeQuizStats(lessonId),
        repository.observeLessonDetail(lessonId).map { it?.lesson?.title.orEmpty() },
    ) { q, a, r, stats, title ->
        when (q) {
            Questions.Loading -> UiState.Loading
            Questions.Failed -> UiState.Error
            is Questions.Loaded -> UiState.Success(QuizUiState(title, q.items, a, r, stats.best))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState.Loading)

    init {
        viewModelScope.launch {
            questions.value = runCatching { repository.getQuizQuestions(lessonId) }
                .fold({ Questions.Loaded(it) }, { Questions.Failed })
        }
    }

    fun select(questionId: String, optionId: String) {
        if (result.value != null) return
        answers.update { it + (questionId to optionId) }
    }

    fun submit() {
        val loaded = questions.value as? Questions.Loaded ?: return
        if (submitting || result.value != null || loaded.items.any { it.id !in answers.value }) return
        submitting = true
        viewModelScope.launch {
            try {
                result.value = repository.submitQuiz(lessonId, answers.value)
            } finally {
                submitting = false
            }
        }
    }

    fun retry() {
        answers.value = emptyMap()
        result.value = null
    }
}
