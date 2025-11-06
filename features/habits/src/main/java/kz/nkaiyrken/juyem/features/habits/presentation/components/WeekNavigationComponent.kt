package kz.nkaiyrken.juyem.features.habits.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import kz.nkaiyrken.juyem.core.ui.theme.Gray500
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.theme.LightGray100
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors
import kz.nkaiyrken.juyem.core.util.DateUtils.getWeekEnd
import kz.nkaiyrken.juyem.core.util.DateUtils.getWeekStart
import kz.nkaiyrken.juyem.features.habits.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun WeekNavigationComponent(
    onPreviousWeekClick: () -> Unit,
    onNextWeekClick: () -> Unit,
    modifier: Modifier = Modifier,
    currentWeekStartDate: LocalDate = LocalDate.now(),
    canNavigateToNext: Boolean = true,
    canNavigateToPrevious: Boolean = true,
) {
    val weekEndDate = getWeekEnd(currentWeekStartDate)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(LightGray100)
            .padding(vertical = 4.dp, horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left arrow button
        NavigationButton(
            onClick = onPreviousWeekClick,
            enabled = canNavigateToPrevious,
            isLeft = true
        )

        // Date range text
        Text(
            text = formatWeekRange(currentWeekStartDate, weekEndDate),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.additionalColors.elementsHighContrast,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Right arrow button
        NavigationButton(
            onClick = onNextWeekClick,
            enabled = canNavigateToNext,
            isLeft = false
        )
    }
}

@Composable
private fun NavigationButton(
    onClick: () -> Unit,
    enabled: Boolean,
    isLeft: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(LightGray100)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (isLeft) {
                Icons.AutoMirrored.Filled.KeyboardArrowLeft
            } else {
                Icons.AutoMirrored.Filled.KeyboardArrowRight
            },
            contentDescription = if (isLeft) stringResource(R.string.previous_week)
            else stringResource(R.string.next_week),
            tint = Gray500.copy(alpha = if (enabled) 1f else 0.38f),
            modifier = Modifier.size(24.dp)
        )
    }
}

private fun formatWeekRange(startDate: LocalDate, endDate: LocalDate): String {
    val locale = Locale("ru", "RU")
    val dayFormatter = DateTimeFormatter.ofPattern("d", locale)
    val monthFormatter = DateTimeFormatter.ofPattern("MMM", locale)

    val startDay = startDate.format(dayFormatter)
    val endDay = endDate.format(dayFormatter)

    // If months are the same, show month only once
    return if (startDate.month == endDate.month) {
        val month = startDate.format(monthFormatter)
        "$startDay–$endDay $month"
    } else {
        val startMonth = startDate.format(monthFormatter)
        val endMonth = endDate.format(monthFormatter)
        "$startDay $startMonth – $endDay $endMonth"
    }
}

// ========== Previews ==========

@Preview(name = "Week Navigation - Current", showBackground = true)
@Composable
private fun WeekNavigationCurrentPreview() {
    JuyemTheme {
        WeekNavigationComponent(
            currentWeekStartDate = LocalDate.of(2024, 10, 22),
            onPreviousWeekClick = {},
            onNextWeekClick = {},
            canNavigateToPrevious = true,
            canNavigateToNext = true
        )
    }
}

@Preview(name = "Week Navigation - Different Months", showBackground = true)
@Composable
private fun WeekNavigationDifferentMonthsPreview() {
    JuyemTheme {
        WeekNavigationComponent(
            currentWeekStartDate = LocalDate.of(2024, 9, 30),
            onPreviousWeekClick = {},
            onNextWeekClick = {},
            canNavigateToPrevious = true,
            canNavigateToNext = true
        )
    }
}

@Preview(name = "Week Navigation - No Next", showBackground = true)
@Composable
private fun WeekNavigationNoNextPreview() {
    JuyemTheme {
        WeekNavigationComponent(
            currentWeekStartDate = LocalDate.of(2024, 10, 22),
            onPreviousWeekClick = {},
            onNextWeekClick = {},
            canNavigateToPrevious = true,
            canNavigateToNext = false
        )
    }
}

@Preview(name = "Week Navigation - No Previous", showBackground = true)
@Composable
private fun WeekNavigationNoPreviousPreview() {
    JuyemTheme {
        WeekNavigationComponent(
            currentWeekStartDate = LocalDate.of(2024, 1, 1),
            onPreviousWeekClick = {},
            onNextWeekClick = {},
            canNavigateToPrevious = false,
            canNavigateToNext = true
        )
    }
}

@Preview(name = "Week Navigation - Interactive", showBackground = true)
@Composable
private fun WeekNavigationInteractivePreview() {
    JuyemTheme {
        var currentWeek by remember {
            mutableStateOf(getWeekStart(LocalDate.now()))
        }

        WeekNavigationComponent(
            currentWeekStartDate = currentWeek,
            onPreviousWeekClick = {
                currentWeek = currentWeek.minusWeeks(1)
            },
            onNextWeekClick = {
                currentWeek = currentWeek.plusWeeks(1)
            },
            canNavigateToPrevious = true,
            canNavigateToNext = currentWeek.plusWeeks(1) <= getWeekStart(LocalDate.now())
        )
    }
}
