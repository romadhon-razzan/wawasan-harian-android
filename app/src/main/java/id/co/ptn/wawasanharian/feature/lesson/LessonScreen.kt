package id.co.ptn.wawasanharian.feature.lesson

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import id.co.ptn.wawasanharian.R
import id.co.ptn.wawasanharian.domain.LessonStatus
import id.co.ptn.wawasanharian.domain.SectionKind
import id.co.ptn.wawasanharian.ui.UiState
import id.co.ptn.wawasanharian.ui.appContainer
import id.co.ptn.wawasanharian.ui.components.LoadingContent
import id.co.ptn.wawasanharian.ui.components.MessageContent
import id.co.ptn.wawasanharian.ui.components.ParagraphText
import id.co.ptn.wawasanharian.ui.components.SectionTitle
import id.co.ptn.wawasanharian.ui.components.StatusLabel
import id.co.ptn.wawasanharian.ui.components.successColor

@Composable
fun LessonRoute(
    lessonId: String,
    onBack: () -> Unit,
    onOpenLesson: (String) -> Unit,
    onOpenQuiz: (String) -> Unit,
) {
    val container = appContainer()
    val viewModel = viewModel(key = "lesson-$lessonId") { LessonViewModel(lessonId, container.learningRepository) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val title = (state as? UiState.Success)?.data?.detail?.let {
        stringResource(R.string.lesson_number, it.lesson.number, it.totalLessons)
    }.orEmpty()

    LessonScaffold(title = title, onBack = onBack) { modifier ->
        when (val s = state) {
            UiState.Loading -> LoadingContent(modifier)
            UiState.Error -> MessageContent(stringResource(R.string.error_generic), modifier)
            is UiState.Success -> {
                val data = s.data
                if (data == null) {
                    MessageContent(stringResource(R.string.lesson_not_found), modifier)
                } else {
                    LessonScreen(
                        state = data,
                        onMarkCompleted = viewModel::markCompleted,
                        onOpenLesson = onOpenLesson,
                        onOpenQuiz = { onOpenQuiz(lessonId) },
                        modifier = modifier,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScaffold(title: String, onBack: () -> Unit, content: @Composable (Modifier) -> Unit) {
    Scaffold(
        // The root scaffold already applies system-bar insets.
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(painterResource(R.drawable.ic_arrow_back), contentDescription = stringResource(R.string.action_back))
                    }
                },
                windowInsets = WindowInsets(0),
            )
        },
    ) { padding -> content(Modifier.padding(padding)) }
}

@Composable
fun LessonScreen(
    state: LessonUiState,
    onMarkCompleted: () -> Unit,
    onOpenLesson: (String) -> Unit,
    onOpenQuiz: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val detail = state.detail
    val lesson = detail.lesson
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    stringResource(R.string.stage_label_with_title, detail.stage.order, detail.stage.title),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary,
                )
                Text(lesson.title, style = MaterialTheme.typography.headlineSmall, modifier = Modifier.semantics { heading() })
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(R.string.minutes, lesson.estimatedMinutes), style = MaterialTheme.typography.labelLarge)
                    StatusLabel(lesson.status)
                }
                Text(lesson.summary, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        itemsIndexed(state.blocks, key = { index, _ -> "block-$index" }) { _, block ->
            when (block) {
                is LessonBlock.Section -> SectionBlock(block)
                is LessonBlock.KeyTerms -> KeyTermsBlock(block)
                is LessonBlock.Examples -> ExamplesBlock(block)
                is LessonBlock.Discussion -> DiscussionBlock(block)
                is LessonBlock.Quiz -> QuizBlock(block, state, onOpenQuiz)
            }
        }
        item {
            if (lesson.status == LessonStatus.COMPLETED) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(painterResource(R.drawable.ic_check_circle), contentDescription = null, tint = successColor())
                    Text(stringResource(R.string.lesson_completed), style = MaterialTheme.typography.titleMedium)
                }
            } else {
                Button(onClick = onMarkCompleted, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)) {
                    Text(stringResource(R.string.action_mark_complete))
                }
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                detail.previousLessonId?.let { id ->
                    OutlinedButton(onClick = { onOpenLesson(id) }, modifier = Modifier.weight(1f).heightIn(min = 48.dp)) {
                        Icon(painterResource(R.drawable.ic_arrow_back), contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.size(6.dp))
                        Text(stringResource(R.string.action_previous))
                    }
                } ?: Spacer(Modifier.weight(1f))
                detail.nextLessonId?.let { id ->
                    OutlinedButton(onClick = { onOpenLesson(id) }, modifier = Modifier.weight(1f).heightIn(min = 48.dp)) {
                        Text(stringResource(R.string.action_next))
                        Spacer(Modifier.size(6.dp))
                        Icon(painterResource(R.drawable.ic_arrow_forward), contentDescription = null, modifier = Modifier.size(18.dp))
                    }
                } ?: Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun stepLabel(step: Int): String {
    val kind = stringResource(
        when (step) {
            1 -> R.string.lesson_kind_kenali
            2 -> R.string.lesson_kind_ketahui
            3 -> R.string.lesson_kind_hubungkan
            4 -> R.string.lesson_kind_diskusikan
            5 -> R.string.lesson_kind_uji
            else -> R.string.lesson_kind_rangkum
        }
    )
    return stringResource(R.string.lesson_step, step, kind).uppercase()
}

@Composable
private fun StepHeader(step: Int, title: String) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(stepLabel(step), style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.secondary)
        SectionTitle(title)
    }
}

@Composable
private fun SectionBlock(block: LessonBlock.Section) {
    val content: @Composable () -> Unit = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            StepHeader(block.step, block.section.title)
            ParagraphText(block.section.body)
        }
    }
    if (block.section.kind == SectionKind.RANGKUM) {
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
            Column(Modifier.padding(16.dp)) { content() }
        }
    } else {
        content()
    }
}

@Composable
private fun KeyTermsBlock(block: LessonBlock.KeyTerms) {
    OutlinedCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            SectionTitle(stringResource(R.string.lesson_key_terms))
            block.terms.forEachIndexed { i, term ->
                if (i > 0) HorizontalDivider()
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(term.term, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                    Text(term.definition, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
private fun ExamplesBlock(block: LessonBlock.Examples) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SectionTitle(stringResource(R.string.lesson_examples))
        block.examples.forEach { example ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("•", style = MaterialTheme.typography.bodyLarge)
                Text(example, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
private fun DiscussionBlock(block: LessonBlock.Discussion) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        StepHeader(block.step, stringResource(R.string.lesson_discussion_title))
        Text(stringResource(R.string.lesson_discussion_intro), style = MaterialTheme.typography.bodyMedium)
        block.prompts.forEachIndexed { index, prompt ->
            var expanded by rememberSaveable(prompt.prompt) { mutableStateOf(false) }
            OutlinedCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("${index + 1}. ${prompt.prompt}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                    if (expanded) {
                        Text(prompt.exampleResponse, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    TextButton(onClick = { expanded = !expanded }, modifier = Modifier.heightIn(min = 48.dp)) {
                        Text(stringResource(if (expanded) R.string.lesson_hide_answer else R.string.lesson_show_answer))
                    }
                }
            }
        }
    }
}

@Composable
private fun QuizBlock(block: LessonBlock.Quiz, state: LessonUiState, onOpenQuiz: () -> Unit) {
    val best = state.quizStats.best
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            StepHeader(block.step, stringResource(R.string.lesson_quiz_title))
            Text(stringResource(R.string.lesson_quiz_body, block.questionCount), style = MaterialTheme.typography.bodyMedium)
            if (best != null) {
                Text(
                    stringResource(R.string.lesson_quiz_best, best.score, best.totalQuestions),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
            Button(onClick = onOpenQuiz, modifier = Modifier.heightIn(min = 48.dp)) {
                Text(stringResource(if (best == null) R.string.action_start_quiz else R.string.action_retake_quiz))
            }
        }
    }
}
