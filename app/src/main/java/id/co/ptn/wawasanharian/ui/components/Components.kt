package id.co.ptn.wawasanharian.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.isSystemInDarkTheme
import id.co.ptn.wawasanharian.R
import id.co.ptn.wawasanharian.domain.LessonStatus
import id.co.ptn.wawasanharian.ui.theme.SuccessDark
import id.co.ptn.wawasanharian.ui.theme.SuccessLight

@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun MessageContent(message: String, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
        Text(message, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
    }
}

@Composable
fun ScreenHeader(title: String, subtitle: String?, modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.semantics { heading() },
        )
        if (subtitle != null) {
            Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun SectionTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier.semantics { heading() },
    )
}

/** Renders text whose paragraphs are separated by blank lines, avoiding long unbroken blocks. */
@Composable
fun ParagraphText(text: String, modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        text.split("\n\n").filter { it.isNotBlank() }.forEach { paragraph ->
            Text(paragraph.trim(), style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun successColor(): Color = if (isSystemInDarkTheme()) SuccessDark else SuccessLight

@Composable
fun statusText(status: LessonStatus): String = stringResource(
    when (status) {
        LessonStatus.NOT_STARTED -> R.string.status_not_started
        LessonStatus.IN_PROGRESS -> R.string.status_in_progress
        LessonStatus.COMPLETED -> R.string.status_completed
    }
)

/** Decorative status icon; the status is always also shown as text (never color/shape alone). */
@Composable
fun LessonStatusIcon(status: LessonStatus, modifier: Modifier = Modifier) {
    val (icon, tint) = when (status) {
        LessonStatus.NOT_STARTED -> R.drawable.ic_circle_outline to MaterialTheme.colorScheme.onSurfaceVariant
        LessonStatus.IN_PROGRESS -> R.drawable.ic_play_circle to MaterialTheme.colorScheme.secondary
        LessonStatus.COMPLETED -> R.drawable.ic_check_circle to successColor()
    }
    Icon(painterResource(icon), contentDescription = null, tint = tint, modifier = modifier.size(24.dp))
}

@Composable
fun StatusLabel(status: LessonStatus, modifier: Modifier = Modifier) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        LessonStatusIcon(status, Modifier.size(18.dp))
        Text(statusText(status), style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun LabeledProgress(fraction: Float, label: String, modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        LinearProgressIndicator(
            progress = { fraction.coerceIn(0f, 1f) },
            color = MaterialTheme.colorScheme.primary,
            // Default track (secondaryContainer) disappears on gold cards; surfaceVariant contrasts everywhere.
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(label, style = MaterialTheme.typography.bodyMedium)
    }
}
