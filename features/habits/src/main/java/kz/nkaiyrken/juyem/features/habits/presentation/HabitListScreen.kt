package kz.nkaiyrken.juyem.features.habits.presentation

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
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import kz.nkaiyrken.juyem.features.habits.presentation.models.HabitAction
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


@Composable
fun HabitListScreen(
    modifier: Modifier = Modifier,
    viewModel: HabitListViewModel = hiltViewModel(),
    topAppBarState: TopAppBarState,
    onNavigateToDetail: (Int) -> Unit = {},
    onNavigateToCreate: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var expandedHabitId by remember { mutableStateOf<Int?>(null) }

    var selectedDay by remember { mutableStateOf(LocalDate.now().dayOfWeek) }

    val topBarTitle = getTopBarTitle(selectedDay, expandedHabitId != null)

    LaunchedEffect(topBarTitle) {
        topAppBarState.update(
            title = topBarTitle,
            showNavigationIcon = false, // Main screen - no back button
            actionText = expandedHabitId?.let { "Детали" },
            onActionTextClick = { expandedHabitId?.let { onNavigateToDetail(it) } },
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
            currentWeekStartDate = uiState.currentWeekStartDate,
            onPreviousWeekClick = viewModel::onPreviousWeekClick,
            onNextWeekClick = viewModel::onNextWeekClick,
            canNavigateToPrevious = !(uiState.contentState is HabitListUiState.ContentState.Empty),
            canNavigateToNext = uiState.currentWeekStartDate.plusWeeks(1) <= getWeekStart(LocalDate.now()),
        )

        Spacer(modifier = Modifier.height(24.dp))

        when (val contentState = uiState.contentState) {
            is HabitListUiState.ContentState.Loading -> HabitListLoadingContent()

            is HabitListUiState.ContentState.Error -> HabitListErrorContent(contentState)

            is HabitListUiState.ContentState.Empty -> HabitListEmptyContent()

            is HabitListUiState.ContentState.Success -> {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = contentState.habits,
                        key = { it.habitId }
                    ) { habitCardUiModel ->
                        val isExpanded = expandedHabitId == habitCardUiModel.habitId

                        val dayToShow = if (isExpanded) selectedDay else LocalDate.now().dayOfWeek

                        HabitCardItem(
                            uiModel = habitCardUiModel,
                            selectedDay = dayToShow,
                            selectedDayProgress = habitCardUiModel.weekDaysProgress[dayToShow]!!,
                            expanded = isExpanded,
                            onExpandClick = {
                                if (isExpanded) {
                                    selectedDay = LocalDate.now().dayOfWeek
                                    expandedHabitId = null
                                } else {
                                    selectedDay = LocalDate.now().dayOfWeek
                                    expandedHabitId = habitCardUiModel.habitId
                                }
                            },
                            onAction = { action ->
                                if (action is HabitAction.DayChipClick) {
                                    if (isExpanded) {
                                        selectedDay = action.dailyProgressUiModel.date.dayOfWeek
                                    }
                                }
                                viewModel.handleAction(action)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HabitListEmptyContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.UploadFile,
                contentDescription = stringResource(R.string.add_habit),
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.additionalColors.elementsAccent
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
private fun HabitListErrorContent(state: HabitListUiState.ContentState.Error) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = state.message,
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

private fun getTopBarTitle(selectedDay: DayOfWeek, isAnyCardExpanded: Boolean): String {
    if (!isAnyCardExpanded) {
        return "Активные привычки"
    }

    val today = LocalDate.now()
    val selectedDate = today.with(selectedDay)

    val daysDifference = ChronoUnit.DAYS.between(selectedDate, today).toInt()

    return when {
        daysDifference == 0 -> "Сегодня"
        daysDifference == 1 -> "Вчера"
        else -> {
            val formatter = DateTimeFormatter.ofPattern("d MMMM")
            selectedDate.format(formatter)
        }
    }
}

