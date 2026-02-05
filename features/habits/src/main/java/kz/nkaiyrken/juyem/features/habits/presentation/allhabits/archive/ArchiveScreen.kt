package kz.nkaiyrken.juyem.features.habits.presentation.allhabits.archive

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors
import kz.nkaiyrken.juyem.core.ui.widgets.toolbar.TopAppBarState
import kz.nkaiyrken.juyem.features.habits.R
import kz.nkaiyrken.juyem.features.habits.presentation.allhabits.ReorderUiState
import kz.nkaiyrken.juyem.features.habits.presentation.allhabits.components.ReorderHabitItem

@Composable
fun ArchiveScreen(
    modifier: Modifier = Modifier,
    viewModel: ArchiveViewModel = hiltViewModel(),
    topAppBarState: TopAppBarState,
    onNavigateBack: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val title = stringResource(R.string.archive)
    val actionText = stringResource(R.string.clear_all)

    LaunchedEffect(Unit) {
        topAppBarState.update(
            title = title,
            showNavigationIcon = true,
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onNavigationClick = onNavigateBack,
            actionText = actionText,
            onActionTextClick = viewModel::deleteAllHabits,
        )
    }

    Column(
        modifier = modifier
            .background(MaterialTheme.additionalColors.backgroundPrimary)
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.archive_description),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.additionalColors.elementsLowContrast,
        )

        Spacer(modifier = Modifier.height(24.dp))

        when (val contentState = uiState.contentState) {
            is ReorderUiState.ContentState.Loading -> ArchiveLoadingContent()

            is ReorderUiState.ContentState.Error -> ArchiveErrorContent(contentState.message)

            is ReorderUiState.ContentState.Empty -> ArchiveEmptyContent()

            is ReorderUiState.ContentState.Success -> ArchiveSuccessContent(
                habits = contentState.habits,
                onActivateHabit = viewModel::onActivateHabit,
                onDeleteHabit = viewModel::onDeleteHabit
            )
        }
    }
}

@Composable
private fun ArchiveSuccessContent(
    habits: List<Habit>,
    onActivateHabit: (Int) -> Unit,
    onDeleteHabit: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            items = habits,
            key = { it.id }
        ) { habit ->
            ReorderHabitItem(
                title = habit.title,
                actionText = stringResource(R.string.activate),
                onActionClick = { onActivateHabit(habit.id) },
                icon = Icons.Default.DeleteOutline,
                onIconButtonClick = { onDeleteHabit(habit.id) }
            )
        }
    }
}

@Composable
private fun ArchiveEmptyContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.there_are_no_habits),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.additionalColors.elementsLowContrast,
        )
    }
}

@Composable
private fun ArchiveErrorContent(errorMessage: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
        )
    }
}

@Composable
private fun ArchiveLoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}
