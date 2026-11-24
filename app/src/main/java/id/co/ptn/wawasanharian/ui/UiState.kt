package id.co.ptn.wawasanharian.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import id.co.ptn.wawasanharian.AppContainer
import id.co.ptn.wawasanharian.WawasanApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data object Error : UiState<Nothing>
}

/** Maps a data flow to [UiState], turning failures into a friendly error state instead of a crash. */
fun <T> Flow<T>.asUiState(scope: CoroutineScope): StateFlow<UiState<T>> =
    map<T, UiState<T>> { UiState.Success(it) }
        .catch { emit(UiState.Error) }
        .stateIn(scope, SharingStarted.WhileSubscribed(5_000), UiState.Loading)

@Composable
fun appContainer(): AppContainer = (LocalContext.current.applicationContext as WawasanApp).container

private val indonesian: Locale = Locale.forLanguageTag("id-ID")

fun formatDate(epochMillis: Long): String =
    SimpleDateFormat("d MMM yyyy", indonesian).format(Date(epochMillis))
