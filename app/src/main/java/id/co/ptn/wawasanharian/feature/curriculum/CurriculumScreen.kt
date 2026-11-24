package id.co.ptn.wawasanharian.feature.curriculum

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import id.co.ptn.wawasanharian.R
import id.co.ptn.wawasanharian.domain.LearningRepository
import id.co.ptn.wawasanharian.domain.LearningRules
import id.co.ptn.wawasanharian.domain.LessonSummary
import id.co.ptn.wawasanharian.domain.StageWithLessons
import id.co.ptn.wawasanharian.ui.UiState
import id.co.ptn.wawasanharian.ui.appContainer
import id.co.ptn.wawasanharian.ui.asUiState
import id.co.ptn.wawasanharian.ui.components.LessonStatusIcon
import id.co.ptn.wawasanharian.ui.components.LoadingContent
import id.co.ptn.wawasanharian.ui.components.MessageContent
import id.co.ptn.wawasanharian.ui.components.ScreenHeader
import id.co.ptn.wawasanharian.ui.components.statusText
import kotlinx.coroutines.flow.map

data class CurriculumUiState(val stages: List<StageWithLessons>, val currentLessonId: String?)

class CurriculumViewModel(repository: LearningRepository) : ViewModel() {
    val state = repository.observeCurriculum()
        .map { CurriculumUiState(it, LearningRules.currentLesson(it)?.id) }
        .asUiState(viewModelScope)
}

@Composable
fun CurriculumRoute(onOpenLesson: (String) -> Unit) {
    val container = appContainer()
    val viewModel = viewModel { CurriculumViewModel(container.learningRepository) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    when (val s = state) {
        UiState.Loading -> LoadingContent()
        UiState.Error -> MessageContent(stringResource(R.string.error_generic))
        is UiState.Success -> CurriculumScreen(s.data, onOpenLesson)
    }
}

@Composable
fun CurriculumScreen(state: CurriculumUiState, onOpenLesson: (String) -> Unit) {
    val total = state.stages.sumOf { it.lessons.size }
    LazyColumn(
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            ScreenHeader(
                stringResource(R.string.nav_curriculum),
                stringResource(R.string.curriculum_subtitle),
                Modifier.padding(bottom = 8.dp),
            )
        }
        state.stages.forEach { stage ->
            item(key = stage.stage.id) { StageHeader(stage) }
            items(stage.lessons, key = { it.id }) { lesson ->
                LessonRow(lesson, total, isCurrent = lesson.id == state.currentLessonId) { onOpenLesson(lesson.id) }
            }
        }
    }
}

@Composable
private fun StageHeader(stage: StageWithLessons) {
    val s = stage.stage
    val progress = LearningRules.stageProgress(stage)
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                stringResource(R.string.stage_label, s.order) + " · " +
                    stringResource(R.string.stage_weeks, s.order * 2 - 1, s.order * 2),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
            Text(s.title, style = MaterialTheme.typography.titleLarge, modifier = Modifier.semantics { heading() })
            Text(s.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(
                stringResource(R.string.stage_completed_count, progress.completed, progress.total),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
private fun LessonRow(lesson: LessonSummary, total: Int, isCurrent: Boolean, onClick: () -> Unit) {
    ListItem(
        overlineContent = {
            Text(
                stringResource(R.string.lesson_number, lesson.number, total) +
                    if (isCurrent) " · " + stringResource(R.string.curriculum_current) else "",
            )
        },
        headlineContent = { Text(lesson.title) },
        supportingContent = { Text(statusText(lesson.status)) },
        leadingContent = { LessonStatusIcon(lesson.status) },
        trailingContent = {
            Icon(painterResource(R.drawable.ic_chevron_right), contentDescription = null, modifier = Modifier.size(24.dp))
        },
        colors = ListItemDefaults.colors(
            containerColor = if (isCurrent) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface,
        ),
        modifier = Modifier.clickable(onClick = onClick),
    )
}
