package id.co.ptn.wawasanharian.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import id.co.ptn.wawasanharian.R
import id.co.ptn.wawasanharian.domain.LessonStatus
import id.co.ptn.wawasanharian.domain.StageProgress
import id.co.ptn.wawasanharian.ui.UiState
import id.co.ptn.wawasanharian.ui.appContainer
import id.co.ptn.wawasanharian.ui.components.LabeledProgress
import id.co.ptn.wawasanharian.ui.components.LessonStatusIcon
import id.co.ptn.wawasanharian.ui.components.LoadingContent
import id.co.ptn.wawasanharian.ui.components.MessageContent
import id.co.ptn.wawasanharian.ui.components.SectionTitle
import id.co.ptn.wawasanharian.ui.theme.Gold
import id.co.ptn.wawasanharian.ui.theme.Navy
import id.co.ptn.wawasanharian.ui.theme.TextLight
import id.co.ptn.wawasanharian.ui.theme.TextSecondaryLight

@Composable
fun HomeRoute(
    onOpenLesson: (String) -> Unit,
    onOpenCurriculum: () -> Unit,
) {
    val container = appContainer()
    val viewModel = viewModel { HomeViewModel(container.learningRepository) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    when (val s = state) {
        UiState.Loading -> LoadingContent()
        UiState.Error -> MessageContent(stringResource(R.string.error_generic))
        is UiState.Success -> HomeScreen(s.data, onOpenLesson, onOpenCurriculum)
    }
}

@Composable
fun HomeScreen(
    state: HomeUiState,
    onOpenLesson: (String) -> Unit,
    onOpenCurriculum: () -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    stringResource(state.greetingRes),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.semantics { heading() },
                )
                Text(
                    stringResource(
                        if (state.summary.completedCount == 0 && state.currentLesson?.status == LessonStatus.NOT_STARTED) {
                            R.string.home_welcome
                        } else {
                            R.string.home_tagline
                        }
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        item { CurrentLessonCard(state, onOpenLesson, onOpenCurriculum) }
        item { ProgressCard(state) }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                SectionTitle(stringResource(R.string.home_roadmap_title))
                Text(
                    stringResource(R.string.home_roadmap_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        items(state.stages, key = { it.stage.id }) { stage ->
            StageRow(stage, onClick = onOpenCurriculum)
        }
        if (state.summary.recentlyCompleted.isNotEmpty()) {
            item { SectionTitle(stringResource(R.string.home_recent_title)) }
            items(state.summary.recentlyCompleted, key = { "recent-${it.id}" }) { lesson ->
                OutlinedCard(onClick = { onOpenLesson(lesson.id) }, modifier = Modifier.fillMaxWidth()) {
                    Row(
                        Modifier.padding(16.dp).heightIn(min = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        LessonStatusIcon(lesson.status)
                        Text(lesson.title, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun CurrentLessonCard(
    state: HomeUiState,
    onOpenLesson: (String) -> Unit,
    onOpenCurriculum: () -> Unit,
) {
    // Brand card mirrors the Home Screen widget's look.
    Card(
        colors = CardDefaults.cardColors(containerColor = Navy, contentColor = TextLight),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            val lesson = state.currentLesson
            if (lesson == null) {
                Text(stringResource(R.string.home_all_done_title), style = MaterialTheme.typography.titleLarge)
                Text(stringResource(R.string.home_all_done_body), style = MaterialTheme.typography.bodyMedium, color = TextSecondaryLight)
                GoldButton(stringResource(R.string.action_view_curriculum), onOpenCurriculum)
                return@Column
            }
            val inProgress = lesson.status == LessonStatus.IN_PROGRESS
            Text(
                stringResource(if (inProgress) R.string.home_label_continue else R.string.home_label_today),
                style = MaterialTheme.typography.labelLarge,
                color = Gold,
            )
            state.currentStage?.let {
                Text(
                    stringResource(R.string.stage_label_with_title, it.order, it.title),
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryLight,
                )
            }
            Text(lesson.title, style = MaterialTheme.typography.titleLarge, modifier = Modifier.semantics { heading() })
            Text(lesson.widgetTeaser, style = MaterialTheme.typography.bodyMedium, color = TextSecondaryLight)
            Text(
                stringResource(R.string.lesson_number, lesson.number, state.summary.totalCount) +
                    " · " + stringResource(R.string.minutes, lesson.estimatedMinutes),
                style = MaterialTheme.typography.labelLarge,
                color = TextSecondaryLight,
            )
            GoldButton(
                stringResource(if (inProgress) R.string.action_continue else R.string.action_start),
            ) { onOpenLesson(lesson.id) }
        }
    }
}

@Composable
private fun GoldButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = Navy),
        modifier = Modifier.padding(top = 4.dp).heightIn(min = 48.dp),
    ) {
        Text(label)
        Spacer(Modifier.size(8.dp))
        Icon(painterResource(R.drawable.ic_arrow_forward), contentDescription = null, modifier = Modifier.size(18.dp))
    }
}

@Composable
private fun ProgressCard(state: HomeUiState) {
    OutlinedCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            SectionTitle(stringResource(R.string.home_progress_title))
            LabeledProgress(
                fraction = state.summary.fraction,
                label = stringResource(R.string.progress_completed_count, state.summary.completedCount, state.summary.totalCount),
            )
            Text(
                if (state.completedLast7Days > 0) {
                    stringResource(R.string.home_week_count, state.completedLast7Days)
                } else {
                    stringResource(R.string.home_week_zero)
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun StageRow(progress: StageProgress, onClick: () -> Unit) {
    val stage = progress.stage
    OutlinedCard(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    stringResource(R.string.stage_label, stage.order) + " · " +
                        stringResource(R.string.stage_weeks, stage.order * 2 - 1, stage.order * 2),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary,
                )
                Text(stage.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    stringResource(R.string.stage_completed_count, progress.completed, progress.total),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            if (progress.completed == progress.total && progress.total > 0) {
                LessonStatusIcon(LessonStatus.COMPLETED)
            }
            Icon(
                painterResource(R.drawable.ic_chevron_right),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

