package kz.nkaiyrken.juyem.features.habits.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kz.nkaiyrken.juyem.core.ui.widgets.toolbar.TopAppBarState
import kz.nkaiyrken.juyem.features.habits.presentation.HabitListUiState.Success


@Composable
fun HabitListScreen(
    modifier: Modifier = Modifier,
    viewModel: HabitListViewModel = hiltViewModel(),
    topAppBarState: TopAppBarState,
    onNavigateToDetail: (Int) -> Unit = {},
    onNavigateToCreate: () -> Unit = {},
) {
    // Configure TopAppBar for this screen
    LaunchedEffect(Unit) {
        topAppBarState.update(
            title = "Сегодня",
            showNavigationIcon = false, // Main screen - no back button
            actionText = "Детали",
            onActionTextClick = {
                // TODO: Navigate to details/stats screen
            },
            actionIcon = Icons.Default.MoreVert,
            onActionIconClick = {
                // TODO: Show more options menu
            },
        )
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Habit List Screen")
        // TODO: Add habit list content
    }
}
