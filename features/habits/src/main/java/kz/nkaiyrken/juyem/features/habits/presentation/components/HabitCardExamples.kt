package kz.nkaiyrken.juyem.features.habits.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.widgets.button.CommonPrimaryButton
import kz.nkaiyrken.juyem.core.ui.widgets.chip.DayChipType
import java.time.DayOfWeek

/**
 * Example: Default habit card without extra progress tracking.
 */
@Preview(name = "Default Habit Card", showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun DefaultHabitCardExample() {
    JuyemTheme {
        var expanded by remember { mutableStateOf(true) }
        HabitCardItem(
            title = "На зарядку!",
            weekProgress = mapOf(
                DayOfWeek.MONDAY to DayChipType.Failure,
                DayOfWeek.TUESDAY to DayChipType.Empty,
                DayOfWeek.WEDNESDAY to DayChipType.Success,
                DayOfWeek.THURSDAY to DayChipType.Success,
                DayOfWeek.FRIDAY to DayChipType.Empty,
                DayOfWeek.SATURDAY to DayChipType.Empty,
                DayOfWeek.SUNDAY to DayChipType.Empty
            ),
            expanded = expanded,
            onExpandClick = { expanded = !expanded },
            modifier = Modifier.padding(16.dp),
            expandedContent = {
                DefaultExpandedContent()
            }

        )
    }
}

/**
 * Example: Habit card with count progress.
 */
@Preview(name = "Count Habit Card", showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun CountHabitCardExample() {
    JuyemTheme {
        var expanded by remember { mutableStateOf(true) }

        HabitCardItem(
            title = "Выпить воды",
            weekProgress = mapOf(
                DayOfWeek.MONDAY to DayChipType.Empty,
                DayOfWeek.TUESDAY to DayChipType.Empty,
                DayOfWeek.WEDNESDAY to DayChipType.Success,
                DayOfWeek.THURSDAY to DayChipType.Success,
                DayOfWeek.FRIDAY to DayChipType.Empty,
                DayOfWeek.SATURDAY to DayChipType.Empty,
                DayOfWeek.SUNDAY to DayChipType.Empty
            ),
            expanded = expanded,
            onExpandClick = { expanded = !expanded },
            isProgressShown = true,
            currentProgressValue = 5,
            goalProgressValue = 10,
            unit = "стаканов",
            expandedContent = {
                CounterExpandedContent()
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * Example: Habit card with timer progress.
 */
@Preview(name = "Timer Habit Card", showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun TimerHabitCardExample() {
    JuyemTheme {
        var expanded by remember { mutableStateOf(true) }

        HabitCardItem(
            title = "Медитация",
            weekProgress = mapOf(
                DayOfWeek.MONDAY to DayChipType.Success,
                DayOfWeek.TUESDAY to DayChipType.Success,
                DayOfWeek.WEDNESDAY to DayChipType.Success,
                DayOfWeek.THURSDAY to DayChipType.Empty,
                DayOfWeek.FRIDAY to DayChipType.Empty,
                DayOfWeek.SATURDAY to DayChipType.Empty,
                DayOfWeek.SUNDAY to DayChipType.Empty
            ),
            expanded = expanded,
            onExpandClick = { expanded = !expanded },
            isProgressShown = true,
            currentProgressValue = 3240,
            goalProgressValue = 7220,
            isTimer = true,
            expandedContent = {
                TimerExpandedContent()
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}
