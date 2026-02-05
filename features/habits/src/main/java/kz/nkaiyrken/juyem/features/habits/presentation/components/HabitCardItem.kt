package kz.nkaiyrken.juyem.features.habits.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.nkaiyrken.juyem.core.DailyProgressStatus
import kz.nkaiyrken.juyem.core.HabitType
import kz.nkaiyrken.juyem.core.ui.theme.Gray500
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.theme.LightGray100
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors
import kz.nkaiyrken.juyem.core.ui.widgets.chip.DayChip
import kz.nkaiyrken.juyem.core.ui.widgets.chip.DayChipState
import kz.nkaiyrken.juyem.core.ui.widgets.chip.DayChipType
import kz.nkaiyrken.juyem.core.ui.widgets.chip.getDayLabel
import kz.nkaiyrken.juyem.features.habits.R
import kz.nkaiyrken.juyem.features.habits.presentation.HabitCardAction
import kz.nkaiyrken.juyem.features.habits.presentation.models.HabitCardUiModel
import kz.nkaiyrken.juyem.features.habits.presentation.models.DailyProgressUiModel
import kz.nkaiyrken.juyem.features.habits.presentation.models.NumericProgress
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun HabitCardItem(
    uiModel: HabitCardUiModel,
    selectedDay: DayOfWeek,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onExpandClick: () -> Unit = {},
    onAction: (HabitCardAction) -> Unit = {}
) {
    val selectedDayProgress = requireNotNull(uiModel.weekDaysProgress[selectedDay]) {
        "Selected day $selectedDay not found in weekDaysProgress"
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(LightGray100)
            .clickable(onClick = onExpandClick)
            .padding(16.dp)
    ) {
        // Header with title and chevron
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = uiModel.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.additionalColors.elementsHighContrast,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = if (expanded) {
                    Icons.Default.KeyboardArrowUp
                } else {
                    Icons.Default.KeyboardArrowDown
                },
                contentDescription = if (expanded) stringResource(R.string.collapse)
                else stringResource(R.string.expand),
                tint = Gray500,
                modifier = Modifier.size(24.dp)
            )
        }

        if (selectedDayProgress is NumericProgress) {
            Spacer(modifier = Modifier.height(8.dp))

            if (selectedDayProgress.isEnabled) {
                HabitCardProgress(progress = selectedDayProgress)
            } else {
                HabitCardProgressNotScheduled()
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Week progress chips
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            DayOfWeek.entries.forEach { day ->
                val progress = uiModel.weekDaysProgress[day]
                val type = mapDailyProgressStatusToDayChipType(progress?.status)
                val isEnabled = progress?.isEnabled ?: true

                val state = when {
                    !isEnabled -> DayChipState.DISABLED
                    expanded && day == selectedDay -> DayChipState.FOCUS
                    else -> DayChipState.DEFAULT
                }

                DayChip(
                    dayLabel = getDayLabel(day),
                    type = type,
                    state = state,
                    onClick = {
                        if (isEnabled && progress != null) {
                            onAction(HabitCardAction.DayChipClick(progress))
                        }
                    }
                )
            }
        }

        // Expanded content - varies by habit type
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column {
                Spacer(modifier = Modifier.height(16.dp))
                when (uiModel.habitType) {
                    HabitType.BOOLEAN -> DefaultExpandedContent(
                        enabled = selectedDayProgress.isEnabled,
                        onMarkComplete = { onAction(HabitCardAction.Complete(selectedDayProgress)) },
                        onEditNote = { onAction(HabitCardAction.EditNote(selectedDayProgress)) },
                        onSkip = { onAction(HabitCardAction.Skip(selectedDayProgress)) },
                        onClear = { onAction(HabitCardAction.Clear(selectedDayProgress)) }
                    )
                    HabitType.COUNTER -> CounterExpandedContent(
                        enabled = selectedDayProgress.isEnabled,
                        onConfirm = { count ->
                            onAction(
                                HabitCardAction.ConfirmCounter(
                                dailyProgressUiModel = selectedDayProgress,
                                count = count,
                            ))
                        },
                        onEditNote = { onAction(HabitCardAction.EditNote(selectedDayProgress)) },
                        onSkip = { onAction(HabitCardAction.Skip(selectedDayProgress)) }
                    )
                    HabitType.TIMER -> TimerExpandedContent(
                        enabled = selectedDayProgress.isEnabled,
                        onStartTimer = { onAction(HabitCardAction.StartTimer(selectedDayProgress)) },
                        onMarkComplete = { onAction(HabitCardAction.Complete(selectedDayProgress)) },
                        onEditNote = { onAction(HabitCardAction.EditNote(selectedDayProgress)) },
                        onSkip = { onAction(HabitCardAction.Skip(selectedDayProgress)) },
                        onClear = { onAction(HabitCardAction.Clear(selectedDayProgress)) }
                    )
                }
            }
        }
    }
}

private fun mapDailyProgressStatusToDayChipType(status: DailyProgressStatus?): DayChipType {
    return when (status) {
        DailyProgressStatus.COMPLETED -> DayChipType.COMPLETED
        DailyProgressStatus.PARTIAL -> DayChipType.EMPTY
        DailyProgressStatus.SKIPPED -> DayChipType.SKIPPED
        DailyProgressStatus.FAILED -> DayChipType.FAILED
        DailyProgressStatus.EMPTY -> DayChipType.EMPTY
        null -> DayChipType.EMPTY
    }
}

@Preview(name = "Boolean Habit - Collapsed", showBackground = true)
@Composable
private fun BooleanHabitCollapsedPreview() {
    JuyemTheme {
        Box(
            modifier = Modifier
                .width(400.dp)
                .background(MaterialTheme.additionalColors.elementsHighContrast)
                .padding(16.dp)
        ) {
            val today = LocalDate.now()
            val weekDaysProgress = mapOf(
                DayOfWeek.MONDAY to DailyProgressUiModel.TypeBoolean(
                    habitId = 1,
                    date = today.with(DayOfWeek.MONDAY),
                    status = DailyProgressStatus.COMPLETED,
                    isEnabled = true
                ),
                DayOfWeek.TUESDAY to DailyProgressUiModel.TypeBoolean(
                    habitId = 1,
                    date = today.with(DayOfWeek.TUESDAY),
                    status = DailyProgressStatus.COMPLETED,
                    isEnabled = true
                ),
                DayOfWeek.WEDNESDAY to DailyProgressUiModel.TypeBoolean(
                    habitId = 1,
                    date = today.with(DayOfWeek.WEDNESDAY),
                    status = DailyProgressStatus.SKIPPED,
                    isEnabled = true
                ),
                DayOfWeek.THURSDAY to DailyProgressUiModel.TypeBoolean(
                    habitId = 1,
                    date = today.with(DayOfWeek.THURSDAY),
                    status = DailyProgressStatus.FAILED,
                    isEnabled = true
                ),
                DayOfWeek.FRIDAY to DailyProgressUiModel.TypeBoolean(
                    habitId = 1,
                    date = today.with(DayOfWeek.FRIDAY),
                    status = DailyProgressStatus.COMPLETED,
                    isEnabled = true
                ),
                DayOfWeek.SATURDAY to DailyProgressUiModel.TypeBoolean(
                    habitId = 1,
                    date = today.with(DayOfWeek.SATURDAY),
                    status = DailyProgressStatus.EMPTY,
                    isEnabled = true
                ),
                DayOfWeek.SUNDAY to DailyProgressUiModel.TypeBoolean(
                    habitId = 1,
                    date = today.with(DayOfWeek.SUNDAY),
                    status = DailyProgressStatus.EMPTY,
                    isEnabled = true
                )
            )
            HabitCardItem(
                uiModel = HabitCardUiModel(
                    habitId = 1,
                    title = "Медитация",
                    habitType = HabitType.BOOLEAN,
                    weekDaysProgress = weekDaysProgress
                ),
                selectedDay = today.dayOfWeek
            )
        }
    }
}

@Preview(name = "Counter Habit - With Progress", showBackground = true)
@Composable
private fun CounterHabitWithProgressPreview() {
    JuyemTheme {
        Box(
            modifier = Modifier
                .width(400.dp)
                .background(MaterialTheme.additionalColors.elementsHighContrast)
                .padding(16.dp)
        ) {
            val today = LocalDate.now()
            val weekDaysProgress = mapOf(
                DayOfWeek.MONDAY to DailyProgressUiModel.Counter(
                    habitId = 2,
                    date = today.with(DayOfWeek.MONDAY),
                    status = DailyProgressStatus.FAILED,
                    currentValue = 0,
                    goalValue = 10,
                    unit = "подходов",
                    isEnabled = true
                ),
                DayOfWeek.TUESDAY to DailyProgressUiModel.Counter(
                    habitId = 2,
                    date = today.with(DayOfWeek.TUESDAY),
                    status = DailyProgressStatus.EMPTY,
                    currentValue = 0,
                    goalValue = 10,
                    unit = "подходов",
                    isEnabled = true
                ),
                DayOfWeek.WEDNESDAY to DailyProgressUiModel.Counter(
                    habitId = 2,
                    date = today.with(DayOfWeek.WEDNESDAY),
                    status = DailyProgressStatus.COMPLETED,
                    currentValue = 10,
                    goalValue = 10,
                    unit = "подходов",
                    isEnabled = true
                ),
                DayOfWeek.THURSDAY to DailyProgressUiModel.Counter(
                    habitId = 2,
                    date = today.with(DayOfWeek.THURSDAY),
                    status = DailyProgressStatus.COMPLETED,
                    currentValue = 10,
                    goalValue = 10,
                    unit = "подходов",
                    isEnabled = true
                ),
                DayOfWeek.FRIDAY to DailyProgressUiModel.Counter(
                    habitId = 2,
                    date = today.with(DayOfWeek.FRIDAY),
                    status = DailyProgressStatus.EMPTY,
                    currentValue = 5,
                    goalValue = 10,
                    unit = "подходов",
                    isEnabled = true
                ),
                DayOfWeek.SATURDAY to DailyProgressUiModel.Counter(
                    habitId = 2,
                    date = today.with(DayOfWeek.SATURDAY),
                    status = DailyProgressStatus.EMPTY,
                    currentValue = 0,
                    goalValue = 10,
                    unit = "подходов",
                    isEnabled = true
                ),
                DayOfWeek.SUNDAY to DailyProgressUiModel.Counter(
                    habitId = 2,
                    date = today.with(DayOfWeek.SUNDAY),
                    status = DailyProgressStatus.EMPTY,
                    currentValue = 0,
                    goalValue = 10,
                    unit = "подходов",
                    isEnabled = true
                )
            )
            HabitCardItem(
                uiModel = HabitCardUiModel(
                    habitId = 2,
                    title = "На зарядку!",
                    habitType = HabitType.COUNTER,
                    weekDaysProgress = weekDaysProgress
                ),
                selectedDay = today.dayOfWeek
            )
        }
    }
}

@Preview(name = "Counter Habit - Expanded", showBackground = true)
@Composable
private fun CounterHabitExpandedPreview() {
    JuyemTheme {
        var expanded by remember { mutableStateOf(true) }
        Box(
            modifier = Modifier
                .width(400.dp)
                .background(MaterialTheme.additionalColors.elementsHighContrast)
                .padding(16.dp)
        ) {
            val today = LocalDate.now()
            val weekDaysProgress = mapOf(
                DayOfWeek.MONDAY to DailyProgressUiModel.Counter(
                    habitId = 4,
                    date = today.with(DayOfWeek.MONDAY),
                    status = DailyProgressStatus.COMPLETED,
                    currentValue = 8,
                    goalValue = 8,
                    unit = "стаканов",
                    isEnabled = true
                ),
                DayOfWeek.TUESDAY to DailyProgressUiModel.Counter(
                    habitId = 4,
                    date = today.with(DayOfWeek.TUESDAY),
                    status = DailyProgressStatus.COMPLETED,
                    currentValue = 8,
                    goalValue = 8,
                    unit = "стаканов",
                    isEnabled = true
                ),
                DayOfWeek.WEDNESDAY to DailyProgressUiModel.Counter(
                    habitId = 4,
                    date = today.with(DayOfWeek.WEDNESDAY),
                    status = DailyProgressStatus.COMPLETED,
                    currentValue = 8,
                    goalValue = 8,
                    unit = "стаканов",
                    isEnabled = true
                ),
                DayOfWeek.THURSDAY to DailyProgressUiModel.Counter(
                    habitId = 4,
                    date = today.with(DayOfWeek.THURSDAY),
                    status = DailyProgressStatus.EMPTY,
                    currentValue = 8,
                    goalValue = 8,
                    unit = "стаканов",
                    isEnabled = true
                ),
                DayOfWeek.FRIDAY to DailyProgressUiModel.Counter(
                    habitId = 4,
                    date = today.with(DayOfWeek.FRIDAY),
                    status = DailyProgressStatus.COMPLETED,
                    currentValue = 8,
                    goalValue = 8,
                    unit = "стаканов",
                    isEnabled = true
                ),
                DayOfWeek.SATURDAY to DailyProgressUiModel.Counter(
                    habitId = 4,
                    date = today.with(DayOfWeek.SATURDAY),
                    status = DailyProgressStatus.EMPTY,
                    currentValue = 3,
                    goalValue = 8,
                    unit = "стаканов",
                    isEnabled = false
                ),
                DayOfWeek.SUNDAY to DailyProgressUiModel.Counter(
                    habitId = 4,
                    date = today.with(DayOfWeek.SUNDAY),
                    status = DailyProgressStatus.EMPTY,
                    currentValue = 0,
                    goalValue = 8,
                    unit = "стаканов",
                    isEnabled = true
                )
            )
            HabitCardItem(
                uiModel = HabitCardUiModel(
                    habitId = 4,
                    title = "Выпить воды",
                    habitType = HabitType.COUNTER,
                    weekDaysProgress = weekDaysProgress
                ),
                selectedDay = DayOfWeek.MONDAY,
                expanded = expanded,
                onExpandClick = { expanded = !expanded }
            )
        }
    }
}
