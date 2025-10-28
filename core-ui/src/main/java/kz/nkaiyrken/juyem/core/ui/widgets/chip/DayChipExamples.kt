package kz.nkaiyrken.juyem.core.ui.widgets.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import java.time.DayOfWeek
import java.time.LocalDate

/**
 * Example: Interactive week selector with DayChip.
 * Click on a day to toggle between states.
 */
@Composable
fun WeekProgressExample(
    modifier: Modifier = Modifier,
    weekProgress: Map<DayOfWeek, DayChipType> = emptyMap(),
    onDayClick: (DayOfWeek) -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        DayOfWeek.values().forEach { day ->
            val type = weekProgress[day] ?: DayChipType.Empty
            DayChip(
                dayLabel = getDayLabel(day),
                type = type,
                onClick = { onDayClick(day) }
            )
        }
    }
}

/**
 * Example: Interactive week progress tracker.
 */
@Preview(name = "Interactive week tracker", showBackground = true)
@Composable
private fun InteractiveWeekProgressPreview() {
    JuyemTheme {
        var weekProgress by remember {
            mutableStateOf(
                mapOf(
                    DayOfWeek.MONDAY to DayChipType.Success,
                    DayOfWeek.TUESDAY to DayChipType.Success,
                    DayOfWeek.WEDNESDAY to DayChipType.Skip,
                    DayOfWeek.THURSDAY to DayChipType.Failure,
                    DayOfWeek.FRIDAY to DayChipType.Empty,
                    DayOfWeek.SATURDAY to DayChipType.Empty,
                    DayOfWeek.SUNDAY to DayChipType.Empty
                )
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Tap to toggle state",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            WeekProgressExample(
                weekProgress = weekProgress,
                onDayClick = { day ->
                    val currentType = weekProgress[day] ?: DayChipType.Empty
                    val nextType = when (currentType) {
                        DayChipType.Empty -> DayChipType.Success
                        DayChipType.Success -> DayChipType.Skip
                        DayChipType.Skip -> DayChipType.Failure
                        DayChipType.Failure -> DayChipType.Empty
                        DayChipType.Default -> DayChipType.Success
                    }
                    weekProgress = weekProgress + (day to nextType)
                }
            )
        }
    }
}

/**
 * Example: Habit card with multiple weeks.
 */
@Composable
fun HabitWeekHistory(
    modifier: Modifier = Modifier,
    weeks: List<Map<DayOfWeek, DayChipType>>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        weeks.forEach { weekProgress ->
            WeekProgressExample(weekProgress = weekProgress)
        }
    }
}

/**
 * Example: 4 weeks of habit history.
 */
@Preview(name = "4 weeks history", showBackground = true)
@Composable
private fun HabitWeekHistoryPreview() {
    JuyemTheme {
        val weeks = listOf(
            // Week 1 - Perfect week
            mapOf(
                DayOfWeek.MONDAY to DayChipType.Success,
                DayOfWeek.TUESDAY to DayChipType.Success,
                DayOfWeek.WEDNESDAY to DayChipType.Success,
                DayOfWeek.THURSDAY to DayChipType.Success,
                DayOfWeek.FRIDAY to DayChipType.Success,
                DayOfWeek.SATURDAY to DayChipType.Success,
                DayOfWeek.SUNDAY to DayChipType.Success
            ),
            // Week 2 - One skip
            mapOf(
                DayOfWeek.MONDAY to DayChipType.Success,
                DayOfWeek.TUESDAY to DayChipType.Success,
                DayOfWeek.WEDNESDAY to DayChipType.Skip,
                DayOfWeek.THURSDAY to DayChipType.Success,
                DayOfWeek.FRIDAY to DayChipType.Success,
                DayOfWeek.SATURDAY to DayChipType.Success,
                DayOfWeek.SUNDAY to DayChipType.Success
            ),
            // Week 3 - Mixed
            mapOf(
                DayOfWeek.MONDAY to DayChipType.Success,
                DayOfWeek.TUESDAY to DayChipType.Failure,
                DayOfWeek.WEDNESDAY to DayChipType.Success,
                DayOfWeek.THURSDAY to DayChipType.Skip,
                DayOfWeek.FRIDAY to DayChipType.Success,
                DayOfWeek.SATURDAY to DayChipType.Failure,
                DayOfWeek.SUNDAY to DayChipType.Success
            ),
            // Week 4 - Current week (partial)
            mapOf(
                DayOfWeek.MONDAY to DayChipType.Success,
                DayOfWeek.TUESDAY to DayChipType.Success,
                DayOfWeek.WEDNESDAY to DayChipType.Success,
                DayOfWeek.THURSDAY to DayChipType.Empty,
                DayOfWeek.FRIDAY to DayChipType.Empty,
                DayOfWeek.SATURDAY to DayChipType.Empty,
                DayOfWeek.SUNDAY to DayChipType.Empty
            )
        )

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "4 Week History",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            HabitWeekHistory(weeks = weeks)
        }
    }
}

/**
 * Example: Get chip type based on habit progress.
 */
fun getDayChipTypeFromProgress(
    date: LocalDate,
    isCompleted: Boolean?,
    isSkipped: Boolean?,
    today: LocalDate = LocalDate.now()
): DayChipType {
    return when {
        // Future dates
        date.isAfter(today) -> DayChipType.Empty

        // Today or past
        isCompleted == true -> DayChipType.Success
        isSkipped == true -> DayChipType.Skip
        isCompleted == false -> DayChipType.Failure
        else -> DayChipType.Empty
    }
}
