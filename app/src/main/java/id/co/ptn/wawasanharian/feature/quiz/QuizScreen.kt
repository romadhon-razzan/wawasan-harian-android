package id.co.ptn.wawasanharian.feature.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import id.co.ptn.wawasanharian.R
import id.co.ptn.wawasanharian.domain.QuestionResult
import id.co.ptn.wawasanharian.domain.QuizQuestion
import id.co.ptn.wawasanharian.feature.lesson.LessonScaffold
import id.co.ptn.wawasanharian.ui.UiState
import id.co.ptn.wawasanharian.ui.appContainer
import id.co.ptn.wawasanharian.ui.components.LoadingContent
import id.co.ptn.wawasanharian.ui.components.MessageContent
import id.co.ptn.wawasanharian.ui.components.successColor

@Composable
fun QuizRoute(lessonId: String, onBack: () -> Unit) {
    val container = appContainer()
    val viewModel = viewModel(key = "quiz-$lessonId") { QuizViewModel(lessonId, container.learningRepository) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    LessonScaffold(title = stringResource(R.string.quiz_title), onBack = onBack) { modifier ->
        when (val s = state) {
            UiState.Loading -> LoadingContent(modifier)
            UiState.Error -> MessageContent(stringResource(R.string.error_generic), modifier)
            is UiState.Success ->
                if (s.data.questions.isEmpty()) {
                    MessageContent(stringResource(R.string.quiz_empty), modifier)
                } else {
                    QuizScreen(
                        state = s.data,
                        onSelect = viewModel::select,
                        onSubmit = viewModel::submit,
                        onRetry = viewModel::retry,
                        onBackToLesson = onBack,
                        modifier = modifier,
                    )
                }
        }
    }
}

@Composable
fun QuizScreen(
    state: QuizUiState,
    onSelect: (questionId: String, optionId: String) -> Unit,
    onSubmit: () -> Unit,
    onRetry: () -> Unit,
    onBackToLesson: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val submitted = state.result != null
    // Show the score as soon as the quiz is submitted, and the first question after a retry.
    LaunchedEffect(submitted) { listState.animateScrollToItem(0) }

    LazyColumn(
        state = listState,
        modifier = modifier,
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            if (state.result == null) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(state.lessonTitle, style = MaterialTheme.typography.titleLarge, modifier = Modifier.semantics { heading() })
                    Text(stringResource(R.string.quiz_intro), style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                ResultCard(state, onRetry, onBackToLesson)
            }
        }
        itemsIndexed(state.questions, key = { _, q -> q.id }) { index, question ->
            QuestionCard(
                number = index + 1,
                total = state.questions.size,
                question = question,
                selectedOptionId = state.answers[question.id],
                result = state.result?.results?.firstOrNull { it.questionId == question.id },
                onSelect = { optionId -> onSelect(question.id, optionId) },
            )
        }
        item {
            if (state.result == null) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        stringResource(R.string.quiz_answered_count, state.answeredCount, state.questions.size),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Button(
                        onClick = onSubmit,
                        enabled = state.canSubmit,
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
                    ) { Text(stringResource(R.string.action_submit_quiz)) }
                }
            } else {
                QuizActions(onRetry, onBackToLesson)
            }
        }
    }
}

@Composable
private fun ResultCard(state: QuizUiState, onRetry: () -> Unit, onBackToLesson: () -> Unit) {
    val result = state.result ?: return
    val message = when {
        result.score == result.total -> R.string.quiz_msg_perfect
        result.score * 2 >= result.total -> R.string.quiz_msg_good
        else -> R.string.quiz_msg_keep_going
    }
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier.fillMaxWidth().semantics { liveRegion = LiveRegionMode.Polite },
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                stringResource(R.string.quiz_score, result.score, result.total),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.semantics { heading() },
            )
            Text(stringResource(message), style = MaterialTheme.typography.bodyLarge)
            state.best?.let {
                Text(stringResource(R.string.lesson_quiz_best, it.score, it.totalQuestions), style = MaterialTheme.typography.labelLarge)
            }
            QuizActions(onRetry, onBackToLesson)
        }
    }
}

@Composable
private fun QuizActions(onRetry: () -> Unit, onBackToLesson: () -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth().padding(top = 4.dp)) {
        Button(onClick = onRetry, modifier = Modifier.weight(1f).heightIn(min = 48.dp)) {
            Text(stringResource(R.string.action_retry_quiz))
        }
        OutlinedButton(onClick = onBackToLesson, modifier = Modifier.weight(1f).heightIn(min = 48.dp)) {
            Text(stringResource(R.string.action_back_to_lesson))
        }
    }
}

@Composable
private fun QuestionCard(
    number: Int,
    total: Int,
    question: QuizQuestion,
    selectedOptionId: String?,
    result: QuestionResult?,
    onSelect: (String) -> Unit,
) {
    OutlinedCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                stringResource(R.string.quiz_question_number, number, total),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
            Text(question.question, style = MaterialTheme.typography.titleMedium)
            Column(Modifier.selectableGroup()) {
                question.options.forEach { option ->
                    val selected = option.id == selectedOptionId
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .heightIn(min = 48.dp)
                            .selectable(
                                selected = selected,
                                enabled = result == null,
                                role = Role.RadioButton,
                                onClick = { onSelect(option.id) },
                            )
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(selected = selected, onClick = null, enabled = result == null)
                        Column(Modifier.weight(1f).padding(start = 8.dp)) {
                            Text(option.label, style = MaterialTheme.typography.bodyLarge)
                            if (result != null && option.isCorrect) {
                                OptionTag(R.drawable.ic_check_circle, stringResource(R.string.quiz_option_correct), successColor())
                            } else if (result != null && selected) {
                                OptionTag(R.drawable.ic_cancel, stringResource(R.string.quiz_option_your_choice), MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
            if (result != null) {
                val correctLabel = question.options.first { it.isCorrect }.label
                val feedback = when {
                    result.isCorrect -> stringResource(R.string.quiz_feedback_correct)
                    result.selectedOptionId == null -> stringResource(R.string.quiz_feedback_unanswered, correctLabel)
                    else -> stringResource(R.string.quiz_feedback_incorrect, correctLabel)
                }
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)) {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(feedback, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                        Text(question.explanation, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Composable
private fun OptionTag(icon: Int, label: String, tint: androidx.compose.ui.graphics.Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Icon(painterResource(icon), contentDescription = null, tint = tint, modifier = Modifier.size(16.dp))
        Text(label, style = MaterialTheme.typography.labelLarge, color = tint)
    }
}
