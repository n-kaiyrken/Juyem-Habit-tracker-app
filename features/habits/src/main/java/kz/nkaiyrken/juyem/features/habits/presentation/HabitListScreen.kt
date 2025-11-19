package kz.nkaiyrken.juyem.features.habits.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors
import kz.nkaiyrken.juyem.core.ui.widgets.toolbar.TopAppBarState
import kz.nkaiyrken.juyem.core.util.DateUtils.getWeekStart
import kz.nkaiyrken.juyem.features.habits.R
import kz.nkaiyrken.juyem.features.habits.presentation.components.HabitCardItem
import kz.nkaiyrken.juyem.features.habits.presentation.components.WeekNavigationComponent
import kz.nkaiyrken.juyem.features.habits.presentation.models.HabitCardUiModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun HabitListScreen(
    modifier: Modifier = Modifier,
    viewModel: HabitListViewModel = hiltViewModel(),
    topAppBarState: TopAppBarState,
    onNavigateToDetail: (Int) -> Unit = {},
    onNavigateToCreate: () -> Unit = {},
    onNavigateToTimerScreen: (Int) -> Unit = {},
    openDrawer: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val topBarTitle = when (val title = uiState.topBarTitle) {
        TopBarTitle.ActiveHabits -> stringResource(R.string.active_habits)
        TopBarTitle.Today -> stringResource(R.string.today)
        TopBarTitle.Yesterday -> stringResource(R.string.yesterday)
        TopBarTitle.Tomorrow -> stringResource(R.string.tomorrow)
        is TopBarTitle.CustomDate -> {
            val formatter = DateTimeFormatter.ofPattern("d MMMM")
            title.date.format(formatter)
        }
    }

    LaunchedEffect(topBarTitle, uiState.expandedHabitId) {
        topAppBarState.update(
            title = topBarTitle,
            showNavigationIcon = true,
            onNavigationClick = openDrawer,
            actionText = uiState.expandedHabitId?.let { "Детали" },
            onActionTextClick = { uiState.expandedHabitId?.let { onNavigateToDetail(it) } },
            actionIcon = Icons.Default.Add,
            onActionIconClick = onNavigateToCreate,
        )
    }

    Column(
        modifier = modifier
            .background(MaterialTheme.additionalColors.backgroundPrimary)
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeekNavigationComponent(
            currentWeekStartDate = uiState.selectedWeekStartDate,
            onPreviousWeekClick = viewModel::onPreviousWeekClick,
            onNextWeekClick = viewModel::onNextWeekClick,
            canNavigateToPrevious = !(uiState.contentState is HabitListUiState.ContentState.Empty),
            canNavigateToNext = uiState.selectedWeekStartDate.plusWeeks(1) <= getWeekStart(LocalDate.now()),
        )

        Spacer(modifier = Modifier.height(24.dp))

        when (val contentState = uiState.contentState) {
            is HabitListUiState.ContentState.Loading -> HabitListLoadingContent()

            is HabitListUiState.ContentState.Error -> HabitListErrorContent(contentState.message)

            is HabitListUiState.ContentState.Empty -> HabitListEmptyContent()

            is HabitListUiState.ContentState.Success -> HabitListSuccessContent(
                habits = contentState.habits,
                expandedHabitId = uiState.expandedHabitId,
                selectedDay = uiState.selectedDay,
                onExpandClick = viewModel::onExpandClick,
                onAction = viewModel::handleAction
            )
        }
    }
}

@Composable
private fun HabitListSuccessContent(
    habits: List<HabitCardUiModel>,
    expandedHabitId: Int?,
    selectedDay: DayOfWeek,
    onExpandClick: (Int) -> Unit,
    onAction: (HabitCardAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = habits,
            key = { it.habitId }
        ) { habitCardUiModel ->
            val isExpanded = expandedHabitId == habitCardUiModel.habitId
            val dayToShow = if (isExpanded) selectedDay else LocalDate.now().dayOfWeek

            HabitCardItem(
                uiModel = habitCardUiModel,
                selectedDay = dayToShow,
                expanded = isExpanded,
                onExpandClick = { onExpandClick(habitCardUiModel.habitId) },
                onAction = onAction
            )
        }
    }
}

@Composable
private fun HabitListEmptyContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = kz.nkaiyrken.juyem.core.ui.R.drawable.ic_empty_box),
                contentDescription = stringResource(R.string.add_habit),
                modifier = Modifier.size(250.dp).align(Alignment.CenterHorizontally),
            )
            Text(
                text = stringResource(R.string.there_are_no_habits),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.additionalColors.elementsHighContrast,
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.your_habits_will_be_displayed_here),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.additionalColors.elementsLowContrast,
            )
        }
    }
}

@Composable
private fun HabitListErrorContent(errorMessage: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun HabitListLoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

