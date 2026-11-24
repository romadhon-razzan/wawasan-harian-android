package id.co.ptn.wawasanharian.feature.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import id.co.ptn.wawasanharian.R
import id.co.ptn.wawasanharian.domain.LearningRepository
import id.co.ptn.wawasanharian.domain.LearningRules
import id.co.ptn.wawasanharian.domain.ProgressSummary
import id.co.ptn.wawasanharian.domain.StageProgress
import id.co.ptn.wawasanharian.ui.UiState
import id.co.ptn.wawasanharian.ui.appContainer
import id.co.ptn.wawasanharian.ui.asUiState
import id.co.ptn.wawasanharian.ui.components.LabeledProgress
import id.co.ptn.wawasanharian.ui.components.LessonStatusIcon
import id.co.ptn.wawasanharian.ui.components.LoadingContent
import id.co.ptn.wawasanharian.ui.components.MessageContent
import id.co.ptn.wawasanharian.ui.components.ScreenHeader
import id.co.ptn.wawasanharian.ui.components.SectionTitle
import id.co.ptn.wawasanharian.ui.formatDate
import kotlinx.coroutines.flow.map
import kotlin.math.roundToInt

data class ProgressUiState(val summary: ProgressSummary, val stages: List<StageProgress>)

class ProgressViewModel(repository: LearningRepository) : ViewModel() {
    val state = repository.observeCurriculum()
        .map { ProgressUiState(LearningRules.progressSummary(it, recentLimit = 5), it.map(LearningRules::stageProgress)) }
        .asUiState(viewModelScope)
}

@Composable
fun ProgressRoute(onOpenLesson: (String) -> Unit) {
    val container = appContainer()
    val viewModel = viewModel { ProgressViewModel(container.learningRepository) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    when (val s = state) {
        UiState.Loading -> LoadingContent()
        UiState.Error -> MessageContent(stringResource(R.string.error_generic))
        is UiState.Success -> ProgressScreen(s.data, onOpenLesson)
    }
}

@Composable
fun ProgressScreen(state: ProgressUiState, onOpenLesson: (String) -> Unit) {
    val summary = state.summary
    LazyColumn(
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item { ScreenHeader(stringResource(R.string.nav_progress), stringResource(R.string.progress_subtitle)) }
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        stringResource(R.string.progress_completed_count, summary.completedCount, summary.totalCount),
                        style = MaterialTheme.typography.titleLarge,
                    )
                    LabeledProgress(
                        fraction = summary.fraction,
                        label = stringResource(R.string.progress_percent, (summary.fraction * 100).roundToInt()),
                    )
                    summary.currentStage?.let {
                        Text(
                            stringResource(R.string.progress_current_stage) + ": " +
                                stringResource(R.string.stage_label_with_title, it.order, it.title),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
        item { SectionTitle(stringResource(R.string.progress_per_stage)) }
        items(state.stages, key = { it.stage.id }) { stage ->
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    stringResource(R.string.stage_label_with_title, stage.stage.order, stage.stage.title),
                    style = MaterialTheme.typography.bodyLarge,
                )
                LabeledProgress(
                    fraction = stage.fraction,
                    label = stringResource(R.string.stage_completed_count, stage.completed, stage.total),
                )
            }
        }
        item { SectionTitle(stringResource(R.string.progress_recent), Modifier.padding(top = 8.dp)) }
        if (summary.recentlyCompleted.isEmpty()) {
            item { Text(stringResource(R.string.progress_empty), style = MaterialTheme.typography.bodyLarge) }
        } else {
            items(summary.recentlyCompleted, key = { "recent-${it.id}" }) { lesson ->
                ListItem(
                    headlineContent = { Text(lesson.title) },
                    supportingContent = {
                        lesson.completedAt?.let { Text(stringResource(R.string.progress_recent_item, formatDate(it))) }
                    },
                    leadingContent = { LessonStatusIcon(lesson.status) },
                    modifier = Modifier.clickable { onOpenLesson(lesson.id) },
                )
            }
        }
        item {
            Text(
                stringResource(R.string.progress_no_pressure),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}
