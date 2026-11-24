package id.co.ptn.wawasanharian.feature.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import id.co.ptn.wawasanharian.BuildConfig
import id.co.ptn.wawasanharian.R
import id.co.ptn.wawasanharian.data.seed.SeedCurriculum
import id.co.ptn.wawasanharian.domain.LearningRepository
import id.co.ptn.wawasanharian.ui.appContainer
import id.co.ptn.wawasanharian.ui.components.ScreenHeader
import id.co.ptn.wawasanharian.ui.components.SectionTitle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: LearningRepository) : ViewModel() {
    private val _resetDone = MutableStateFlow(false)
    /** True once after a reset finishes, so the UI can confirm it. */
    val resetDone: StateFlow<Boolean> = _resetDone.asStateFlow()

    fun resetProgress() {
        viewModelScope.launch {
            repository.resetProgress()
            _resetDone.value = true
        }
    }

    fun onResetMessageShown() {
        _resetDone.value = false
    }
}

@Composable
fun SettingsRoute() {
    val container = appContainer()
    val viewModel = viewModel { SettingsViewModel(container.learningRepository) }
    val resetDone by viewModel.resetDone.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val resetMessage = stringResource(R.string.settings_reset_done)
    val scope = rememberCoroutineScope()

    LaunchedEffect(resetDone) {
        if (resetDone) {
            viewModel.onResetMessageShown()
            // Launched outside this effect: clearing the flag restarts the effect and would cancel it.
            scope.launch { snackbarHostState.showSnackbar(resetMessage) }
        }
    }

    Box(Modifier.fillMaxSize()) {
        SettingsScreen(onConfirmReset = viewModel::resetProgress)
        SnackbarHost(snackbarHostState, Modifier.align(Alignment.BottomCenter).padding(16.dp))
    }
}

@Composable
fun SettingsScreen(onConfirmReset: () -> Unit) {
    var showResetDialog by rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        contentPadding = PaddingValues(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item { ScreenHeader(stringResource(R.string.nav_settings), null, Modifier.padding(horizontal = 20.dp)) }
        item {
            ListItem(
                headlineContent = { Text(stringResource(R.string.settings_reset_title)) },
                supportingContent = { Text(stringResource(R.string.settings_reset_body)) },
                modifier = Modifier.clickable(role = Role.Button) { showResetDialog = true }.padding(horizontal = 4.dp),
            )
        }
        item { HorizontalDivider(Modifier.padding(horizontal = 20.dp)) }
        item {
            Column(Modifier.padding(horizontal = 20.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SectionTitle(stringResource(R.string.settings_about_title))
                Text(stringResource(R.string.settings_about_body), style = MaterialTheme.typography.bodyLarge)
            }
        }
        item {
            ListItem(
                headlineContent = { Text(stringResource(R.string.settings_app_version)) },
                supportingContent = { Text(BuildConfig.VERSION_NAME) },
                modifier = Modifier.padding(horizontal = 4.dp),
            )
        }
        item {
            ListItem(
                headlineContent = { Text(stringResource(R.string.settings_content_version)) },
                supportingContent = { Text(SeedCurriculum.VERSION.toString()) },
                modifier = Modifier.padding(horizontal = 4.dp),
            )
        }
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text(stringResource(R.string.settings_reset_dialog_title)) },
            text = { Text(stringResource(R.string.settings_reset_dialog_body)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showResetDialog = false
                        onConfirmReset()
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error),
                ) { Text(stringResource(R.string.action_reset)) }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) { Text(stringResource(R.string.action_cancel)) }
            },
        )
    }
}
