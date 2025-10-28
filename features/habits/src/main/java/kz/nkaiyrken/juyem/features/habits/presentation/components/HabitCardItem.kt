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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.nkaiyrken.juyem.core.ui.theme.Gray500
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.theme.LightGray100
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors
import kz.nkaiyrken.juyem.core.ui.widgets.chip.DayChip
import kz.nkaiyrken.juyem.core.ui.widgets.chip.DayChipType
import kz.nkaiyrken.juyem.core.ui.widgets.chip.getDayLabel
import java.time.DayOfWeek

@Composable
fun HabitCardItem(
    title: String,
    modifier: Modifier = Modifier,
    weekProgress: Map<DayOfWeek, DayChipType> = emptyMap(),
    currentProgressValue: Int? = null,
    goalProgressValue: Int? = null,
    isTimer: Boolean = false,
    unit: String? = null,
    expanded: Boolean = false,
    isProgressShown: Boolean = false,
    onExpandClick: () -> Unit = {},
    onDayClick: (DayOfWeek) -> Unit = {},
    expandedContent: @Composable () -> Unit = {}
) {

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
                text = title,
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
                contentDescription = if (expanded) "Collapse" else "Expand",
                tint = Gray500,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        if (isProgressShown) {
            HabitCardProgress(
                currentProgressValue = currentProgressValue ?: 0,
                goalProgressValue = goalProgressValue ?: 0,
                isTimer = isTimer,
                unit  = unit,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Week progress chips
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            DayOfWeek.entries.forEach { day ->
                val type = weekProgress[day] ?: DayChipType.Empty
                DayChip(
                    dayLabel = getDayLabel(day),
                    type = type,
                    onClick = { onDayClick(day) }
                )
            }
        }

        // Expanded content
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column {
                Spacer(modifier = Modifier.height(16.dp))
                expandedContent()
            }
        }
    }
}

// ========== Previews ==========

@Preview(name = "Collapsed", showBackground = true)
@Composable
private fun HabitCardItemCollapsedPreview() {
    JuyemTheme {
        Box(
            modifier = Modifier
                .width(840.dp)
                .background(MaterialTheme.additionalColors.elementsHighContrast),
        ) {
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
                modifier = Modifier.padding(16.dp),
                isProgressShown = true,
                currentProgressValue = 5,
                goalProgressValue = 10,
                unit = "подходов"
            )
        }
    }
}

@Preview(name = "Expanded", showBackground = true)
@Composable
private fun HabitCardItemExpandedPreview() {
    JuyemTheme {
        var expanded by remember { mutableStateOf(true) }
        HabitCardItem(
            title = "На зарядку!",
            weekProgress = mapOf(
                DayOfWeek.MONDAY to DayChipType.Failure,
                DayOfWeek.TUESDAY to DayChipType.Skip,
                DayOfWeek.WEDNESDAY to DayChipType.Skip,
                DayOfWeek.THURSDAY to DayChipType.Success,
                DayOfWeek.FRIDAY to DayChipType.Skip,
                DayOfWeek.SATURDAY to DayChipType.Empty,
                DayOfWeek.SUNDAY to DayChipType.Empty
            ),
            expanded = expanded,
            onExpandClick = { expanded = !expanded },
            expandedContent = {
                Text(
                    text = "Additional habit details go here...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray500
                )
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Perfect week", showBackground = true)
@Composable
private fun HabitCardItemPerfectWeekPreview() {
    JuyemTheme {
        HabitCardItem(
            title = "Читать 30 минут",
            weekProgress = mapOf(
                DayOfWeek.MONDAY to DayChipType.Success,
                DayOfWeek.TUESDAY to DayChipType.Success,
                DayOfWeek.WEDNESDAY to DayChipType.Success,
                DayOfWeek.THURSDAY to DayChipType.Success,
                DayOfWeek.FRIDAY to DayChipType.Success,
                DayOfWeek.SATURDAY to DayChipType.Success,
                DayOfWeek.SUNDAY to DayChipType.Success
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Mixed progress", showBackground = true)
@Composable
private fun HabitCardItemMixedProgressPreview() {
    JuyemTheme {
        HabitCardItem(
            title = "Медитация",
            weekProgress = mapOf(
                DayOfWeek.MONDAY to DayChipType.Success,
                DayOfWeek.TUESDAY to DayChipType.Success,
                DayOfWeek.WEDNESDAY to DayChipType.Skip,
                DayOfWeek.THURSDAY to DayChipType.Failure,
                DayOfWeek.FRIDAY to DayChipType.Success,
                DayOfWeek.SATURDAY to DayChipType.Skip,
                DayOfWeek.SUNDAY to DayChipType.Empty
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}
