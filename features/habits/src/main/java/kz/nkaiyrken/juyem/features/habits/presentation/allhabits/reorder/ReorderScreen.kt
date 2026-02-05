package kz.nkaiyrken.juyem.features.habits.presentation.allhabits.reorder

import android.os.Build
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DragIndicator
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.HapticFeedbackConstantsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors
import kz.nkaiyrken.juyem.core.ui.widgets.toolbar.TopAppBarState
import kz.nkaiyrken.juyem.features.habits.R
import kz.nkaiyrken.juyem.features.habits.presentation.allhabits.ReorderUiState
import kz.nkaiyrken.juyem.features.habits.presentation.allhabits.components.ReorderHabitItem
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
fun ReorderScreen(
    modifier: Modifier = Modifier,
    viewModel: ReorderViewModel = hiltViewModel(),
    topAppBarState: TopAppBarState,
    onNavigateBack: () -> Unit = {},
    onNavigateToCreate: () -> Unit = {},
    onNavigateToArchive: () -> Unit = {},
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val title = stringResource(R.string.reorder)
    val actionText = stringResource(R.string.archive)

    LaunchedEffect(Unit) {
        topAppBarState.update(
            title = title,
            showNavigationIcon = true,
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onNavigationClick = onNavigateBack,
            actionText = actionText,
            onActionTextClick = onNavigateToArchive,
            actionIcon = Icons.Default.Add,
            onActionIconClick = onNavigateToCreate,
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
            text = stringResource(R.string.active_journals),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.additionalColors.elementsHighContrast,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.reorder_description),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.additionalColors.elementsLowContrast,
        )

        Spacer(modifier = Modifier.height(24.dp))

        when (val contentState = uiState.contentState) {
            is ReorderUiState.ContentState.Loading -> ReorderLoadingContent(
                modifier = Modifier.weight(1f)
            )

            is ReorderUiState.ContentState.Error -> ReorderErrorContent(
                errorMessage = contentState.message,
                modifier = Modifier.weight(1f)
            )

            is ReorderUiState.ContentState.Empty -> ReorderEmptyContent(
                modifier = Modifier.weight(1f)
            )

            is ReorderUiState.ContentState.Success -> ReorderSuccessContent(
                habits = contentState.habits,
                onArchiveHabit = viewModel::onArchiveHabit,
                onMoveHabit = viewModel::onMoveHabit,
                onDragEnd = viewModel::onDragEnd,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun ReorderSuccessContent(
    habits: List<Habit>,
    onArchiveHabit: (Int) -> Unit,
    onMoveHabit: (Int, Int) -> Unit,
    onDragEnd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val view = LocalView.current
    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        onMoveHabit(from.index, to.index)
    }

    LazyColumn(
        state = lazyListState,
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            items = habits,
            key = { it.id }
        ) { habit ->
            ReorderableItem(
                state = reorderableLazyListState,
                key = habit.id,
            ) { isDragging ->
                ReorderHabitItem(
                    title = habit.title,
                    actionText = stringResource(R.string.to_archive),
                    onActionClick = { onArchiveHabit(habit.id) },
                    icon = Icons.Default.DragIndicator,
                    dragHandleModifier = Modifier.draggableHandle(
                        onDragStarted = {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                                view.performHapticFeedback(HapticFeedbackConstantsCompat.DRAG_START)
                            }
                        },
                        onDragStopped = {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                view.performHapticFeedback(HapticFeedbackConstantsCompat.GESTURE_END)
                            }
                            onDragEnd()
                        },
                    ),
                )
            }
        }
    }
}

@Composable
private fun ReorderEmptyContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
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
private fun ReorderErrorContent(errorMessage: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
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
private fun ReorderLoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}
