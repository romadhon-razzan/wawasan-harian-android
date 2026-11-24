package id.co.ptn.wawasanharian.feature.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.ptn.wawasanharian.R
import id.co.ptn.wawasanharian.domain.LearningRepository
import id.co.ptn.wawasanharian.domain.LearningRules
import id.co.ptn.wawasanharian.domain.LearningStage
import id.co.ptn.wawasanharian.domain.LessonSummary
import id.co.ptn.wawasanharian.domain.ProgressSummary
import id.co.ptn.wawasanharian.domain.StageProgress
import id.co.ptn.wawasanharian.ui.UiState
import id.co.ptn.wawasanharian.ui.asUiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import java.util.concurrent.TimeUnit

data class HomeUiState(
    @param:StringRes val greetingRes: Int,
    /** Null when every lesson is completed. */
    val currentLesson: LessonSummary?,
    val currentStage: LearningStage?,
    val summary: ProgressSummary,
    val completedLast7Days: Int,
    val stages: List<StageProgress>,
)

class HomeViewModel(
    repository: LearningRepository,
    private val clock: () -> Long = System::currentTimeMillis,
) : ViewModel() {

    val state: StateFlow<UiState<HomeUiState>> = repository.observeCurriculum()
        .map { stages ->
            val now = clock()
            val current = LearningRules.currentLesson(stages)
            HomeUiState(
                greetingRes = greetingFor(Calendar.getInstance().apply { timeInMillis = now }.get(Calendar.HOUR_OF_DAY)),
                currentLesson = current,
                currentStage = current?.let { c -> stages.first { it.stage.id == c.stageId }.stage },
                summary = LearningRules.progressSummary(stages),
                completedLast7Days = LearningRules.completedSince(stages, now - TimeUnit.DAYS.toMillis(7)),
                stages = stages.map(LearningRules::stageProgress),
            )
        }
        .asUiState(viewModelScope)
}

@StringRes
fun greetingFor(hourOfDay: Int): Int = when (hourOfDay) {
    in 4..10 -> R.string.home_greeting_morning
    in 11..14 -> R.string.home_greeting_afternoon
    in 15..17 -> R.string.home_greeting_evening
    else -> R.string.home_greeting_night
}
